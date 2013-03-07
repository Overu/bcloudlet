package org.cloudlet.web.core.server;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.shared.CorePackage;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
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
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = CorePackage.CONTENT, typeClass = ResourceType.class)
@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Content {

  private static final Logger logger = Logger.getLogger(Content.class.getName());

  @Context
  @Transient
  protected ResourceContext resourceContext;

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

  @Type(type = CorePackage.CONTENT)
  @Columns(columns = { @Column(name = "parentType"), @Column(name = "parentId") })
  protected Content parent;

  @Transient
  private Service service;

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
  public abstract void delete();

  @Path("{path}")
  public <T extends Content> T getChild(@PathParam("path") String path) {
    T result = findChild(path);
    initResource(result);
    return result;
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
    if (CorePackage.TITLE.equals(name)) {
      return title;
    }
    if (CorePackage.PATH.equals(name)) {
      return path;
    }
    return null;
  }

  public String getResourceType() {
    XmlType type = getClass().getAnnotation(XmlType.class);
    return type.name();
  }

  @XmlTransient
  public Service getService() {
    if (service == null) {
      service = WebPlatform.get().getInstance(getServiceType());
    }
    return service;
  }

  @XmlTransient
  public Class<? extends Service> getServiceType() {
    return Service.class;
  }

  /**
   * @return the summary
   */
  public String getSummary() {
    return summary;
  }

  public String getTitle() {
    return title;
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

  public void joinSQL(StringBuilder sql) {
  }

  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml" })
  public Content load() {
    doLoad();
    return this;
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
    String path = params.getFirst(CorePackage.PATH);
    String title = params.getFirst(CorePackage.TITLE);
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

  public void setParent(Content parent) {
    this.parent = parent;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setPropertyValue(String name, String value) {
    if (CorePackage.TITLE.equals(name)) {
      title = value;
    } else if (CorePackage.PATH.equals(name)) {
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
  public abstract Content update();

  protected Content createFrom(MultivaluedMap<String, String> params) {
    String resourceType = params.getFirst(CorePackage.RESOURCE_TYPE);
    // Class<?> type = Registry.getResourceType(resourceType);
    // if (type != null) {
    // ImplementedBy impl = type.getAnnotation(ImplementedBy.class);
    // Class<? extends ResourceBean> resType = (Class<? extends ResourceBean>) impl.value();
    // ResourceBean result = create(resType);
    // return result;
    // }
    return null;
  }

  protected abstract void doLoad();

  protected EntityManager em() {
    return WebPlatform.get().getEntityManager();
  }

  protected abstract <T extends Content> T findChild(String path);

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

  protected abstract void init();

  protected void initResource(Object result) {
    if (result != null) {
      if (resourceContext != null) {
        resourceContext.initResource(result);
      }
    }
  }

}
