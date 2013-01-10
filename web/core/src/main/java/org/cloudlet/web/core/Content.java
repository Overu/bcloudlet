package org.cloudlet.web.core;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@TypeDef(name = "resource", typeClass = ResourceType.class)
@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Content {

  private static final Logger logger = Logger.getLogger(Content.class.getName());

  public static final String ID = "id";

  public static final String TITLE = "title";

  public static final String PATH = "path";

  public static final String URI = "uri";

  public static final String VERSION = "version";

  public static final String RENDITION = "rendition";

  public static final String CHILDREN = "children";

  public static final String SELF = "";

  public static final String RESOURCE_TYPE = "resourceType";

  public static final String PARENT_TYPE = "parentType";

  public static final String PARENT_ID = "parentId";

  protected String title;

  @Context
  @Transient
  protected ResourceContext resourceContext;

  @Id
  protected String id;

  protected String path;

  @Version
  protected long version;

  @ManyToOne
  protected User owner;

  @Type(type = "resource")
  @Columns(columns = { @Column(name = "parentType"), @Column(name = "parentId") })
  protected Content parent;

  public static final String CHILDREN_COUNT = "childrenCount";

  protected long childrenCount;

  @Transient
  private List<Content> children;

  protected String content;

  public static final String CONTENT = "content";

  @Transient
  @QueryParam(CHILDREN)
  protected boolean loadChildren;

  @QueryParam(RENDITION)
  @Transient
  protected String renditionKind;

  public <T extends Content> T create(Class<T> type) {
    T result = WebPlatform.get().getInstance(type);
    result.setParent(this);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Transient
  public Content createChild(Content child) {
    // check if child path conflicts
    child.setParent(this);
    child.save();

    final Relationship rel = new Relationship();
    rel.setId(UUID.randomUUID().toString());
    rel.setSource(this);
    rel.setTarget(child);
    rel.setPath(child.getPath());
    saveAndCommit(rel);
    setChildrenCount(childrenCount + 1);
    save();
    return child;
  }

  public Content createFromInputStream(@Context UriInfo uriInfo, @QueryParam("path") Integer contentLength,
      @HeaderParam("Content-Type") String contentType, InputStream inputStream) {
    return null;
  }

  @POST
  @Consumes({ MediaType.MULTIPART_FORM_DATA })
  @Produces({ MediaType.APPLICATION_JSON })
  public Content createFromMultipartFormData(@Context UriInfo uriInfo, @HeaderParam("Content-Length") final Integer contentLength,
      @HeaderParam("Content-Type") final String contentType, final InputStream inputStream) {
    Content result = null;
    MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
    try {
      FileUpload fileUpload = new FileUpload();
      FileItemIterator iter = fileUpload.getItemIterator(new RequestContext() {
        @Override
        public String getCharacterEncoding() {
          return "UTF-8";
        }

        @Override
        public int getContentLength() {
          return contentLength;
        }

        @Override
        public String getContentType() {
          return contentType;
        }

        @Override
        public InputStream getInputStream() throws IOException {
          return inputStream;
        }
      });
      MultivaluedMap<String, Media> files = new MultivaluedHashMap<String, Media>();
      while (iter.hasNext()) {
        FileItemStream value = iter.next();
        String key = value.getFieldName();
        InputStream in = value.openStream();
        try {
          if (value.isFormField()) {
            String strValue = Streams.asString(in, "UTF-8");
            params.add(key, strValue);
          } else {
            Media media = WebPlatform.get().getInstance(Media.class);
            media.read(in);
            media.setPath(key);
            media.setTitle(value.getName());
            media.setMimeType(value.getContentType());
            media.save();
            files.add(key, media);
          }
        } finally {
          IOUtils.closeQuietly(in);
        }
      }
      result = createFrom(params);
      if (result != null) {
        result.readParams(params);
        result.readMedia(files);
        result.save();
      }
    } catch (Exception e) {
      // VirusFoundException, VirusFoundException will be handled by
      // ServiceEndPointUtil centrally
      if (e.getCause() != null && e.getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
        throw new WebApplicationException();
      } else if (e instanceof FileUploadBase.SizeLimitExceededException) {
        throw new WebApplicationException();
      } else {
        throw new WebApplicationException();
      }
    } finally {
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  @DELETE
  public void delete() {
    execute(new MethodInvocation() {
      @Override
      protected Object proceed() {
        em().remove(Content.this);
        return null;
      }
    });
  }

  @Path("{path}")
  public Content getByPath(@PathParam("path") String path) {
    Content result = doGetByPath(path);
    if (result != null) {
      if (resourceContext != null) {
        resourceContext.initResource(result);
      }
    }
    return result;
  }

  public Content getChild(String path) {
    try {
      TypedQuery<Relationship> query =
          em().createQuery("from " + Relationship.class.getName() + " rel where rel.source=:source and rel.path=:path", Relationship.class);
      query.setParameter("source", this);
      query.setParameter("path", path);
      return query.getSingleResult().getTarget();
    } catch (NoResultException e) {
      return null;
    }
  }

  @XmlElement
  public List<Content> getChildren() {
    return children;
  }

  public long getChildrenCount() {
    return childrenCount;
  }

  public String getContent() {
    return content;
  }

  public String getId() {
    return id;
  }

  public User getOwner() {
    return owner;
  }

  @XmlTransient
  public Content getParent() {
    return parent;
  }

  public String getPath() {
    return path;
  }

  public Object getPropertyValue(String name) {
    if (TITLE.equals(name)) {
      return title;
    }
    if (PATH.equals(name)) {
      return path;
    }
    return null;
  }

  @XmlTransient
  public String getRenditionKind() {
    return renditionKind;
  }

  public abstract String getResourceType();

  public String getTitle() {
    return title;
  }

  @XmlElement
  public String getUri() {
    return getUriBuilder().toString();
  }

  @XmlTransient
  public StringBuilder getUriBuilder() {
    return getUriBuilder(true);
  }

  public StringBuilder getUriBuilder(boolean includeParams) {
    StringBuilder builder;
    if (getParent() == null) {
      builder = new StringBuilder("/");
    } else {
      builder = getParent().getUriBuilder(false);
      if (builder.length() > 1) {
        builder.append("/");
      }
      builder.append(path);
    }
    return builder;
  }

  public long getVersion() {
    return version;
  }

  public boolean hasChildren() {
    return childrenCount > 0;
  }

  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml" })
  public Content load() {
    doLoad();
    return this;
  }

  public void loadChildren() {
    TypedQuery<Relationship> query =
        em().createQuery("from " + Relationship.class.getName() + " rel where rel.source=:source", Relationship.class);
    query.setParameter("source", this);
    List<Relationship> rels = query.getResultList();
    children = new ArrayList<Content>(rels.size());
    for (Relationship rel : rels) {
      children.add(rel.getTarget());
    }
  }

  public void readFrom(Content delta) {
    if (delta.title != null) {
      this.title = delta.title;
    }
    if (delta.path != null) {
      this.path = delta.path;
    }
  }

  public void readMedia(MultivaluedMap<String, Media> params) {
  }

  public void readParams(MultivaluedMap<String, String> params) {
    String path = params.getFirst(PATH);
    String title = params.getFirst(TITLE);
    if (path != null) {
      this.path = path;
    }
    if (title != null) {
      this.title = title;
    }
  }

  public Content save() {
    if (path != null && parent != null) {
      Content exist = parent.getByPath(path);
      if (exist != null && (id == null || !exist.equals(this))) {
        throw new EntityExistsException("A child with path=" + path + " already exists");
      }
    }
    if (id == null) {
      id = UUID.randomUUID().toString();
    }
    if (path == null) {
      path = id;
    }

    saveAndCommit(this);

    // for (Method m : resource.getClass().getMethods()) {
    // if (m.getParameterTypes().length > 0) {
    // continue;
    // }
    // Path p = m.getAnnotation(Path.class);
    // Class<?> rt = m.getReturnType();
    // if (p != null && Resource.class.isAssignableFrom(rt)) {
    // Class<Resource> childType = (Class<Resource>) rt;
    // Resource result = ClassUtil.newInstance(childType);
    // result.setPath(p.value());
    // DefaultFields fields = m.getAnnotation(DefaultFields.class);
    // if (fields != null) {
    // for (DefaultField field : fields.value()) {
    // result.setPropertyValue(field.key(), field.value());
    // }
    // } else {
    // DefaultField field = m.getAnnotation(DefaultField.class);
    // if (field != null) {
    // result.setPropertyValue(field.key(), field.value());
    // }
    // }
    // resource.createChild(result);
    // }
    // }
    return this;
  }

  public void setChildren(List<Content> children) {
    this.children = children;
  }

  public void setChildrenCount(long totalResults) {
    this.childrenCount = totalResults;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setParent(Content parent) {
    this.parent = parent;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setPropertyValue(String name, String value) {
    if (TITLE.equals(name)) {
      title = value;
    } else if (PATH.equals(name)) {
      path = value;
    } else if (CHILDREN_COUNT.equals(name)) {
      childrenCount = value == null ? 0 : Long.valueOf(value);
    }
  }

  public void setRenditionKind(String renditionKind) {
    this.renditionKind = renditionKind;
  }

  public void setResourceType(String type) {
    // do nothing;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUri(String value) {
    // do nothing
  }

  public void setVersion(long version) {
    this.version = version;
  }

  protected Content createFrom(MultivaluedMap<String, String> params) {
    String resourceType = params.getFirst(RESOURCE_TYPE);
    // Class<?> type = Registry.getResourceType(resourceType);
    // if (type != null) {
    // ImplementedBy impl = type.getAnnotation(ImplementedBy.class);
    // Class<? extends ResourceBean> resType = (Class<? extends ResourceBean>) impl.value();
    // ResourceBean result = create(resType);
    // return result;
    // }
    return null;
  }

  protected Content doGetByPath(String path) {
    Content result = getChild(path);
    if (result == null) {
      Object propValue = getPropertyValue(path);
      if (propValue != null && propValue instanceof Content) {
        result = (Content) propValue;
      }
    }
    return result;
  }

  protected void doLoad() {
    if (loadChildren) {
      loadChildren();
    }
  }

  protected EntityManager em() {
    return WebPlatform.get().getEntityManager();
  }

  protected Object execute(MethodInvocation action, Class<Exception>... ignores) {
    EntityManager em = em();
    final EntityTransaction txn = em.getTransaction();
    // Allow 'joining' of transactions if there is an enclosing
    // @Transactional method.
    if (txn.isActive()) {
      return action.proceed();
    }

    txn.begin();
    Object result;
    try {
      result = action.proceed();
    } catch (Exception e) {
      boolean commit = false;
      for (Class<? extends Exception> ignore : ignores) {
        if (ignore.isInstance(e)) {
          commit = true;
          break;
        }
      }
      if (!commit) {
        txn.rollback();
      } else {
        txn.commit();
      }
      throw transformException(e);
    } finally {
      // Close the em if necessary (guarded so this code doesn't run
      // unless catch fired).
    }

    // everything was normal so commit the txn (do not move into try block
    // above as it
    // interferes with the advised method's throwing semantics)
    try {
      txn.commit();
    } finally {
      // close the em if necessary
    }
    return result;
  }

  protected Object getObject(String type, String id) {
    try {
      Class<?> cls = Class.forName(type);
      if (Enum.class.isAssignableFrom(cls)) {
        Class<? extends Enum> enumClass = (Class<? extends Enum>) cls;
        return Enum.valueOf(enumClass, id);
      } else if (Content.class.isAssignableFrom(cls)) {
        return em().find(cls, id);
      }
    } catch (ClassNotFoundException e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  protected void saveAndCommit(final Object rel) {
    execute(new MethodInvocation() {
      @Override
      protected Object proceed() {
        em().persist(rel);
        return null;
      };
    });
  };

  private RuntimeException transformException(Exception e) {
    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    }
    throw new WebApplicationException(e);
  }
}
