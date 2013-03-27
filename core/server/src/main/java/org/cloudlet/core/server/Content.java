package org.cloudlet.core.server;

import com.google.inject.persist.Transactional;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@TypeDef(name = ContentType.NAME, typeClass = ContentType.class)
@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Content {

  @Transactional
  public static class ContentService {

    protected <T extends Content> T createChild(Content parent, T child) {
      return parent.doCreate(child);
    }

    protected void delete(Content content) {
      content.doDelete();
    }

    protected <T extends Content> T save(T content) {
      content.doSave();
      return content;
    }

    protected void update(Content content) {
      content.doUpdate();
    }
  }

  private static final Logger logger = Logger.getLogger(Content.class.getName());

  public static final String TYPE = "type";

  @Context
  @Transient
  protected ResourceContext resourceContext;

  @Context
  @Transient
  protected UriInfo uriInfo;

  @Id
  @Column(length = 128)
  protected String id;

  protected String path;

  protected String title;

  protected String summary;

  protected Date created;

  @OneToOne
  protected User createdBy;

  @OneToOne
  protected User updatedBy;

  protected Date updated;

  @Version
  protected long version;

  @ManyToOne
  protected User owner;

  @Type(type = ContentType.NAME)
  @Columns(columns = { @Column(name = "parentType"), @Column(name = "parentId") })
  protected Content parent;

  public static final String SEARCH = "search";

  public static final String HOME = "";

  public static final String EDIT = "edit";

  public static final String TOTAL = "total";

  public static final String ID = "id";

  public static final String PARENT_TYPE = "parentType";

  public static final String PARENT_ID = "parentId";

  public static final String TITLE = "title";

  public static final String PATH = "path";

  public static final String VERSION = "version";

  public static final String URI = "uri";

  public void addJoin(StringBuilder sql) {
  }

  public void addWhere(StringBuilder sql) {
  }

  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
  public final Content create() {
    Content content = null;
    return createChild(content);
  }

  public final <T extends Content> T createChild(String path, Class<T> type) {
    T result = WebPlatform.get().getInstance(type);
    result.setPath(path);
    return createChild(result);
  }

  public final <T extends Content> T createChild(T child) {
    return WebPlatform.get().getInstance(ContentService.class).createChild(this, child);
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
            Media media = new Media();
            media.read(in);
            media.setPath(key);
            media.setTitle(value.getName());
            media.setMimeType(value.getContentType());

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
        result.update();
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

  @DELETE
  public final void delete() {
    WebPlatform.get().getInstance(ContentService.class).delete(this);
  }

  public <T extends Content> T doCreate(T child) {
    child.setParent(this);
    String id = child.getId();
    String path = child.getPath();
    if (path != null) {
      Content exist = getChild(path);
      if (exist != null) {
        throw new EntityExistsException("A child with path=" + path + " already exists");
      }
    }

    if (id == null) {
      child.setId(CoreUtil.randomID());
    }

    if (path == null) {
      child.setPath(child.getId());
    }
    em().persist(child);
    if (child.doInit()) {
      em().persist(child);
    }
    return child;
  }

  public void doDelete() {
    em().remove(this);
  }

  public void doLoad() {
  }

  public void doSave() {
    if (id == null) {
      id = CoreUtil.randomID();
    }
    if (path == null) {
      path = id;
    }
    em().persist(this);
    if (doInit()) {
      em().persist(this);
    }
  }

  public void doUpdate() {
    // TODO validation
    Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
    if (path == null) {
    }
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException("", violations);
    }
    if (parent != null) {
      Content exist = parent.getChild(path);
      if (exist != null && !exist.equals(this)) {
        throw new EntityExistsException("A child with path=" + path + " already exists");
      }
    }
    em().persist(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Content)) {
      return false;
    }
    Content that = (Content) obj;
    if (this.id == null || that.id == null) {
      return false;
    }
    return (this.id.equals(that.id));
  }

  @Path("{path}")
  public <T extends Content> T getChild(@PathParam("path") String path) {
    return null;
  }

  public Date getCreated() {
    return created;
  }

  public User getCreatedBy() {
    return createdBy;
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
    if (Content.TITLE.equals(name)) {
      return title;
    }
    if (Content.PATH.equals(name)) {
      return path;
    }
    return null;
  }

  @XmlTransient
  public Content getRoot() {
    if (parent == null) {
      return this;
    }
    return parent.getRoot();
  }

  public String getSummary() {
    return summary;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    Entity type = getClass().getAnnotation(Entity.class);
    if (type != null) {
      return type.name();
    } else {
      return getClass().getSimpleName();
    }
  }

  public Date getUpdated() {
    return updated;
  }

  public User getUpdatedBy() {
    return updatedBy;
  }

  @XmlElement
  public String getUri() {
    URI uri = getUriBuilder().build();
    return uri.getPath();
  }

  @XmlTransient
  public UriBuilder getUriBuilder() {
    UriBuilder builder = parent.getUriBuilder();
    builder.path(path);
    return builder;
  }

  public long getVersion() {
    return version;
  }

  @GET
  @Path("{template}")
  @Produces({ MediaType.TEXT_HTML })
  public Viewable getView(@PathParam("template") String templateName) {
    doLoad();
    Viewable v = new Viewable(templateName, this);
    return v;
  }

  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml" })
  public Content load() {
    doLoad();
    return this;
  }

  @GET
  @Produces({ MediaType.TEXT_HTML })
  @Template(name = "home.jsp")
  public Content loadHtml() {
    doLoad();
    return this;
  }

  public <T extends Content> T newChild(String path, Class<T> clz) {
    T result = WebPlatform.get().getInstance(clz);
    result.setParent(this);
    result.setPath(path);
    return result;
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
    String path = params.getFirst(Content.PATH);
    String title = params.getFirst(Content.TITLE);
    if (path != null) {
      this.path = path;
    }
    if (title != null) {
      this.title = title;
    }
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setParams(TypedQuery query) {
  }

  public void setParent(Content parent) {
    this.parent = parent;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setPropertyValue(String name, String value) {
    if (Content.TITLE.equals(name)) {
      title = value;
    } else if (Content.PATH.equals(name)) {
      path = value;
    }
  }

  public void setResourceType(String type) {
    // do nothing;
  }

  /**
   * @param summary the summary to set
   */
  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUpdated(Date dateUpdated) {
    this.updated = dateUpdated;
  }

  public void setUpdatedBy(User updatedBy) {
    this.updatedBy = updatedBy;
  }

  public void setUri(String value) {
    // do nothing
  }

  public void setVersion(long version) {
    this.version = version;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public final Content update() {
    WebPlatform.get().getInstance(ContentService.class).update(this);
    return this;
  }

  protected Content createFrom(MultivaluedMap<String, String> params) {
    String resourceType = params.getFirst(Content.TYPE);
    // Class<?> type = Registry.getResourceType(resourceType);
    // if (type != null) {
    // ImplementedBy impl = type.getAnnotation(ImplementedBy.class);
    // Class<? extends ResourceBean> resType = (Class<? extends ResourceBean>) impl.value();
    // ResourceBean result = create(resType);
    // return result;
    // }
    return null;
  }

  protected boolean doInit() {
    return false;
  }

  protected EntityManager em() {
    return WebPlatform.get().getEntityManager();
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

  protected void initResource() {
    initResource(this);
  }

  protected void initResource(Object result) {
    if (resourceContext != null) {
      return;
    }
    getParent().initResource(result);
  }

}
