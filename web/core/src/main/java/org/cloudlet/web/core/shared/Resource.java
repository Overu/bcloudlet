package org.cloudlet.web.core.shared;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;

import org.cloudlet.web.core.server.ResourceEntity;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = "content", typeClass = ResourceEntity.class)
@MappedSuperclass
@XmlType(name = Resource.TYPE_NAME)
public abstract class Resource extends Place {

  public static final String TYPE_NAME = "Resource";

  private static final Logger logger = Logger.getLogger(Resource.class.getName());

  public static final ResourceType TYPE = new ResourceType(TYPE_NAME);

  public static String ID = "id";

  public static String TITLE = "title";

  public static String PATH = "path";

  public static String URI = "uri";

  public static String VERSION = "version";

  protected String title;

  @Transient
  protected Object widget;

  @Context
  @Transient
  ResourceContext resourceContext;

  @Id
  protected String id;

  protected String path;

  @Version
  protected long version;

  @ManyToOne
  protected User owner;

  @Type(type = "content")
  @Columns(columns = {@Column(name = "parentType"), @Column(name = "parentId")})
  private Resource parent;

  @Transient
  private transient Object nativeData;

  @Transient
  private transient InputStream contentStream;

  private String mimeType;

  public static final String CHILDREN_COUNT = "childrenCount";

  protected long childrenCount;

  @Transient
  private Map<String, Resource> cache;

  @Transient
  private Map<String, Rendition> allRenditions;

  @Transient
  private Map<String, Rendition> localRenditions;

  @Transient
  private List<Rendition> remoteRenditions;

  public static final String HOME_WIDGET = "";

  protected String content;

  public static final String CONTENT = "content";

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Resource> createFromMultipart(@Context UriInfo uriInfo,
      @HeaderParam("Content-Length") Integer length,
      @HeaderParam("Content-Type") String contentType, InputStream inputStream) {
    DataGraph data = new DataGraph();
    MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
    data.root = getService().createFromMultipart(this, params, contentType, length, inputStream);
    return data;
  }

  public Rendition createRendition(Rendition rendition) {
    rendition.setParent(this);
    rendition.save();
    return rendition;
  }

  @DELETE
  public void delete() {
    getService().delete(this);
  }

  public Resource findChild(final String uri) {
    String[] segments = uri.split("/");
    Resource child = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      Resource parent = child;
      child = parent.getRendition(path);
      if (child != null) {
        return child;
      } else if (parent instanceof Entry) {
        child = ((Entry) parent).getChild(path);
      } else if (parent instanceof Feed) {
        child = ((Feed) parent).getEntry(path);
      }
      if (child == null) {
        if (GWT.isClient()) {
          child = new ResourceProxy();
          child.setParent(parent);
        } else {
          break;
        }
      }
    }
    return child;
  }

  @XmlTransient
  public Map<String, Rendition> getAllRenditions() {
    if (allRenditions == null) {
      allRenditions = new HashMap<String, Rendition>();
      allRenditions.putAll(getLocalRenditions());
      if (remoteRenditions != null) {
        for (Rendition v : remoteRenditions) {
          allRenditions.put(v.getPath(), v);
        }
      }
    }
    return allRenditions;
  }

  public Map<String, Resource> getCache() {
    if (cache == null) {
      cache = new HashMap<String, Resource>();
    }
    return cache;
  }

  public long getChildrenCount() {
    return childrenCount;
  }

  public String getContent() {
    return content;
  }

  public InputStream getContentStream() {
    return contentStream;
  }

  public String getId() {
    return id;
  }

  @XmlTransient
  public Map<String, Rendition> getLocalRenditions() {
    if (localRenditions == null) {
      localRenditions = new HashMap<String, Rendition>();
      for (String key : getResourceType().getWidgetKeys()) {
        if (key.equals(Resource.HOME_WIDGET)) {
          continue;
        }
        Object widget = getResourceType().getWidget(key);
        if (widget != null) {
          Rendition view = new Rendition();
          view.setParent(this);
          view.setPath(key);
          view.setTitle(key);
          localRenditions.put(key, view);
        }
      }
    }
    return localRenditions;
  }

  public String getMimeType() {
    return mimeType;
  }

  public Object getNativeData() {
    return nativeData;
  }

  public User getOwner() {
    return owner;
  }

  @XmlTransient
  public Resource getParent() {
    return parent;
  }

  public String getPath() {
    return path;
  }

  public Object getProperty(String name) {
    if (TITLE.equals(name)) {
      return title;
    }
    if (PATH.equals(name)) {
      return path;
    }
    return null;
  }

  @XmlTransient
  public List<Rendition> getRemoteRenditions() {
    return remoteRenditions;
  }

  public Rendition getRendition(String path) {
    return getAllRenditions().get(path);
  }

  @XmlTransient
  public ResourceType<?> getResourceType() {
    return TYPE;
  }

  public Service getService() {
    return WebPlatform.getInstance().getService(getClass());
  }

  public String getTitle() {
    return title;
  }

  @XmlElement
  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    if (getParent() == null) {
      return new StringBuilder("/");
    }
    StringBuilder builder = getParent().getUriBuilder();
    if (builder.length() > 1) {
      builder.append("/");
    }
    builder.append(path);
    return builder;
  }

  public long getVersion() {
    return version;
  }

  @XmlTransient
  public Object getWidget() {
    if (widget == null) {
      widget = getResourceType().getWidget(HOME_WIDGET);
    }
    return widget;
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml"})
  public DataGraph<Resource> load() {
    doLoad();
    DataGraph<Resource> data = new DataGraph<Resource>();
    data.root = this;
    return data;
  }

  public void readFrom(Resource delta) {
    if (delta.title != null) {
      this.title = delta.title;
    }
    if (delta.path != null) {
      this.path = delta.path;
    }
  }

  public Resource save() {
    return getService().save(this);
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

  public void setNativeData(Object nativeData) {
    this.nativeData = nativeData;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setParent(Resource parent) {
    this.parent = parent;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setProperty(String name, String value) {
    if (TITLE.equals(name)) {
      title = value;
    } else if (PATH.equals(name)) {
      path = value;
    } else if (CHILDREN_COUNT.equals(name)) {
      childrenCount = value == null ? 0 : Long.valueOf(value);
    }
  }

  public void setRemoteRenditions(List<Rendition> remoteViews) {
    this.remoteRenditions = remoteViews;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public void setWidget(Object widget) {
    this.widget = widget;
  }

  public Resource update() {
    return getService().update(this);
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Resource> update(DataGraph<Resource> data) {
    readFrom(data.root);
    update();
    data.root = this;
    return data;
  }

  protected void doLoad() {
  }

}
