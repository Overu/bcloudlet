package org.cloudlet.web.core.bean;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.Resource;
import org.cloudlet.web.core.server.ResourceEntity;
import org.cloudlet.web.core.service.ClassUtil;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@TypeDef(name = "content", typeClass = ResourceEntity.class)
@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class ResourceBean {

  private static final Logger logger = Logger.getLogger(ResourceBean.class.getName());

  public static String ID = "id";

  public static String TITLE = "title";

  public static String PATH = "path";

  public static String URI = "uri";

  public static String VERSION = "version";

  public static String RESOURCE_TYPE = "resourceType";

  public static final String RENDITION = "rendition";

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
  protected UserBean owner;

  @Type(type = "content")
  @Columns(columns = {@Column(name = "parentType"), @Column(name = "parentId")})
  protected ResourceBean parent;

  @Transient
  private transient InputStream contentStream;

  private String mimeType;

  public static final String CHILDREN_COUNT = "childrenCount";

  protected long childrenCount;

  @Transient
  private List<ResourceBean> children;

  public static final String SELF = "";

  protected String content;

  public static final String CONTENT = "content";

  public static final String CHILDREN = "children";

  @Transient
  @QueryParam(CHILDREN)
  protected boolean loadChildren;

  @QueryParam(RENDITION)
  @Transient
  protected String renditionKind;

  public <T extends ResourceBean> T create(Class<T> type) {
    T result = WebPlatform.get().getInstance(type);
    result.setParent(this);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Transient
  public ResourceBean createChild(ResourceBean child) {
    // check if child path conflicts
    child.setParent(this);
    child.save();

    final Relationship rel = new Relationship();
    rel.setId(UUID.randomUUID().toString());
    rel.setSource(this);
    rel.setTarget(child);
    rel.setPath(child.getPath());
    execute(new MethodInvocation() {
      @Override
      protected Object proceed() {
        em().persist(rel);
        return null;
      };
    });
    setChildrenCount(childrenCount + 1);
    save();
    return child;
  }

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public ResourceBean createFromMultipart(@Context UriInfo uriInfo, @HeaderParam("Content-Length") final Integer contentLength,
      @HeaderParam("Content-Type") final String contentType, final InputStream inputStream) {
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

      ResourceBean res = null;
      FileItemStream item = null;
      while (iter.hasNext()) {
        FileItemStream value = iter.next();
        String key = value.getFieldName();
        InputStream in = value.openStream();
        try {
          if (value.isFormField()) {
            String strValue = Streams.asString(in, "UTF-8");
            params.add(key, strValue);
          } else {
            item = value;
          }
        } finally {
          IOUtils.closeQuietly(in);
        }
      }
      if (item != null) {
        String rt = params.getFirst(ResourceBean.RESOURCE_TYPE);
        Class<?> cls = ClassUtil.getClass(rt);
        if (cls != null && ResourceBean.class.isAssignableFrom(cls)) {
          res = WebPlatform.get().getInstance((Class<? extends ResourceBean>) cls);
          res.setContentStream(item.openStream());
          res.setPath(item.getFieldName());
          res.setMimeType(item.getContentType());
          res.setParent(this);
          res.readFrom(params);
          res.save();
        }
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
    return null;
  }

  @SuppressWarnings("unchecked")
  @DELETE
  public void delete() {
    execute(new MethodInvocation() {
      @Override
      protected Object proceed() {
        em().remove(ResourceBean.this);
        return null;
      }
    });
  }

  @Path("{path}")
  public ResourceBean getByPath(@PathParam("path") String path) {
    ResourceBean result = doGetByPath(path);
    if (result != null) {
      if (resourceContext != null) {
        resourceContext.initResource(result);
      }
    }
    return result;
  }

  public ResourceBean getChild(String path) {
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
  public List<ResourceBean> getChildren() {
    return children;
  }

  public long getChildrenCount() {
    return childrenCount;
  }

  public String getContent() {
    return content;
  }

  @XmlTransient
  public InputStream getContentStream() {
    return contentStream;
  }

  @XmlTransient
  public File getFile() {
    String filePath = "D:/DevData/resource/" + getId();
    return new File(filePath);
  }

  public String getId() {
    return id;
  }

  public String getMimeType() {
    return mimeType;
  }

  public UserBean getOwner() {
    return owner;
  }

  @XmlTransient
  public ResourceBean getParent() {
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

  public String getTitle() {
    return title;
  }

  public Class<? extends Resource> getType() {
    return Resource.class;
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
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml"})
  public ResourceBean load() {
    doLoad();
    return this;
  }

  public void loadChildren() {
    TypedQuery<Relationship> query =
        em().createQuery("from " + Relationship.class.getName() + " rel where rel.source=:source", Relationship.class);
    query.setParameter("source", this);
    List<Relationship> rels = query.getResultList();
    children = new ArrayList<ResourceBean>(rels.size());
    for (Relationship rel : rels) {
      children.add(rel.getTarget());
    }
  }

  public void readFrom(MultivaluedMap<String, String> params) {
    String path = params.getFirst(ResourceBean.PATH);
    String title = params.getFirst(ResourceBean.TITLE);
    if (path != null) {
      this.path = path;
    }
    if (title != null) {
      this.title = title;
    }
  }

  public void readFrom(ResourceBean delta) {
    if (delta.title != null) {
      this.title = delta.title;
    }
    if (delta.path != null) {
      this.path = delta.path;
    }
  }

  public ResourceBean save() {
    if (path != null && parent != null) {
      ResourceBean exist = parent.getByPath(path);
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

    execute(new MethodInvocation() {
      @Override
      protected Object proceed() {
        transactionalSave();
        return null;
      };
    });

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

  public void saveResource(InputStream inputStream) {
    InputStream in = null;
    OutputStream out = null;
    try {
      File file = getFile();
      file.getParentFile().mkdirs();
      file.createNewFile();
      in = new BufferedInputStream(inputStream);
      out = new FileOutputStream(file);
      byte[] buffer = new byte[1024];
      for (int bytesRead = in.read(buffer); bytesRead > 0; bytesRead = in.read(buffer)) {
        out.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
  }

  public void setChildren(List<ResourceBean> children) {
    this.children = children;
  }

  public void setChildrenCount(long totalResults) {
    this.childrenCount = totalResults;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setContentStream(InputStream inputStream) {
    this.contentStream = inputStream;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public void setOwner(UserBean owner) {
    this.owner = owner;
  }

  public void setParent(ResourceBean parent) {
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

  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(Class<? extends Resource> type) {
    // do nothing;
  }

  public void setUri(String value) {
    // do nothing
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public void writeResource(OutputStream out) throws IOException {
    InputStream in = null;
    try {
      File file = getFile();
      in = new BufferedInputStream(new FileInputStream(file));
      byte[] buffer = new byte[1024];
      for (int bytesRead = in.read(buffer); bytesRead > 0; bytesRead = in.read(buffer)) {
        out.write(buffer, 0, bytesRead);
      }
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
  }

  protected ResourceBean doGetByPath(String path) {
    ResourceBean result = getChild(path);
    if (result == null) {
      Object propValue = getPropertyValue(path);
      if (propValue != null && propValue instanceof ResourceBean) {
        result = (ResourceBean) propValue;
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
      } else if (ResourceBean.class.isAssignableFrom(cls)) {
        return em().find(cls, id);
      }
    } catch (ClassNotFoundException e) {
      logger.severe(e.getMessage());
    }
    return null;
  };

  protected void transactionalSave() {
    em().persist(this);
    InputStream stream = getContentStream();
    if (stream != null) {
      saveResource(stream);
    }
  }

  private RuntimeException transformException(Exception e) {
    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    }
    throw new WebApplicationException(e);
  }
}
