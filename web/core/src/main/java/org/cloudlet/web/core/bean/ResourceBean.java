package org.cloudlet.web.core.bean;

import com.google.gwt.core.shared.GWT;

import org.cloudlet.web.core.server.ResourceEntity;
import org.cloudlet.web.core.service.ResourceService;
import org.cloudlet.web.core.shared.Resource;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
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
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@TypeDef(name = "content", typeClass = ResourceEntity.class)
@MappedSuperclass
// @XmlType(name = Resource.TYPE_NAME)
public abstract class ResourceBean {

  public static final String TYPE_NAME = "Resource";

  private static final Logger logger = Logger.getLogger(ResourceBean.class.getName());

  public static final ResourceType<ResourceBean> TYPE = new ResourceType<ResourceBean>(TYPE_NAME);

  public static String ID = "id";

  public static String TITLE = "title";

  public static String PATH = "path";

  public static String URI = "uri";

  public static String VERSION = "version";

  public static String RESOURCE_TYPE = "resourceType";

  public static final String RENDITION = "rendition";

  protected String title;

  @Transient
  protected Object widget;

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

  @XmlTransient
  @Transient
  public ResourceBean createChild(ResourceBean child) {
    return getService().createChild(this, child);
  }

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public ResourceBean createFromMultipart(@Context UriInfo uriInfo,
      @HeaderParam("Content-Length") Integer contentLength,
      @HeaderParam("Content-Type") String contentType, InputStream inputStream) {
    ResourceBean res =
        getService().createFromMultipart(this, uriInfo, inputStream, contentType, contentLength);
    return res;
  }

  @DELETE
  public void delete() {
    getService().delete(this);
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
    ResourceBean result = null;
    if (GWT.isClient()) {
      if (children != null) {
        for (ResourceBean child : children) {
          if (path.equals(child.getPath())) {
            return child;
          }
        }
      }
    } else {
      result = getService().getChild(this, path);
    }
    return result;
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

  public ResourceBean getRelationship(Property prop) {
    if (prop.getTargetType() instanceof ResourceType) {
      ResourceBean result = (ResourceBean) getPropertyValue(prop.getPath());
      return result;
    } else {
      return null;
    }
  }

  @XmlTransient
  public String getRenditionKind() {
    return renditionKind;
  }

  @XmlTransient
  public ResourceType<? extends ResourceBean> getResourceType() {
    return TYPE;
  }

  @XmlTransient
  public ResourceService getService() {
    return WebPlatform.getInstance().getService(getClass());
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

  @XmlTransient
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

  @XmlTransient
  public Object getWidget() {
    if (widget == null) {
      String kind = renditionKind == null ? SELF : renditionKind;
      widget = getResourceType().getWidget(kind);
    }
    return widget;
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
    children = getService().findChildren(this);
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
    return getService().save(this);
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

  public void setWidget(Object widget) {
    this.widget = widget;
  }

  public ResourceBean update() {
    return getService().update(this);
  }

  protected ResourceBean doGetByPath(String path) {
    Property prop = getResourceType().getProperty(path);
    ResourceBean result;
    if (prop != null) {
      result = getRelationship(prop);
    } else {
      result = getChild(path);
    }
    return result;
  }

  protected void doLoad() {
    if (loadChildren) {
      loadChildren();
    }
  }

}
