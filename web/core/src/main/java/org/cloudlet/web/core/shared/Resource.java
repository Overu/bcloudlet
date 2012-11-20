package org.cloudlet.web.core.shared;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.Place;

import org.cloudlet.web.core.server.ResourceEntity;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.InputStream;
import java.util.ArrayList;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = "content", typeClass = ResourceEntity.class)
@MappedSuperclass
@XmlType(name = Resource.TYPE_NAME)
public abstract class Resource extends Place implements IsResource {

  public static final String TYPE_NAME = "Resource";

  private static final Logger logger = Logger.getLogger(Resource.class.getName());

  public static final ResourceType<Resource> TYPE = new ResourceType<Resource>(TYPE_NAME);

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
  protected Resource parent;

  @Transient
  private transient Object nativeData;

  @Transient
  private transient InputStream contentStream;

  private String mimeType;

  public static final String CHILDREN_COUNT = "childrenCount";

  protected long childrenCount;

  @Transient
  private List<Resource> children;

  public static final String HOME = "";

  protected String content;

  public static final String CONTENT = "content";

  public static final String CHILDREN = "children";

  @Transient
  @QueryParam(CHILDREN)
  protected boolean loadChildren;

  @QueryParam(RENDITION)
  @Transient
  protected String renditionKind;

  @Transient
  public Map<String, Rendition> renditions;

  @XmlTransient
  @Transient
  private MultivaluedMap<String, String> queryParameters;

  public void addChild(Resource resource) {
    if (children == null) {
      children = new ArrayList<Resource>();
    }
    children.add(resource);
    resource.setParent(this);
  }

  @Override
  public Resource asResource() {
    return this;
  }

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Resource> create(DataGraph<Resource> data) {
    data.root = createChild(data.root);
    return data;
  }

  public Resource createChild(Resource child) {
    return getService().createChild(this, child);
  }

  @POST
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Resource> createFromMultipart(@Context UriInfo uriInfo,
      @HeaderParam("Content-Length") Integer contentLength,
      @HeaderParam("Content-Type") String contentType, InputStream inputStream) {
    DataGraph data = new DataGraph();
    data.root =
        getService().createFromMultipart(this, uriInfo, inputStream, contentType, contentLength);
    return data;
  }

  @DELETE
  public void delete() {
    getService().delete(this);
  }

  @Path("{path}")
  public Resource getByPath(@PathParam("path") String path) {
    Property prop = getResourceType().getProperty(path);
    Resource result;
    if (prop != null) {
      result = getRelationship(prop);
    } else {
      result = getChild(path);
    }
    if (result != null) {
      if (resourceContext != null) {
        resourceContext.initResource(result);
      }
    }
    return result;
  }

  public Resource getByUri(String uri) {
    String[] parts = uri.split("\\?");
    String[] segments = parts[0].split("/");
    Resource parent = this;
    Resource result = null;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      result = parent.getByPath(path);
      if (result == null) {
        if (GWT.isClient()) {
          result = new DynaResource();
          result.setParent(parent);
          result.setPath(path);
        } else {
          break;
        }
      }
      parent = result;
    }
    if (result != null && parts.length > 1) {

      String queryString = parts[1];
      String[] params = queryString.split("&");
      MultivaluedMap<String, String> paramMap = new MultivaluedHashMap<String, String>();
      for (String param : params) {
        int index = param.indexOf("=");
        String paramName = index >= 0 ? param.substring(0, index) : param;
        String paramValue = index >= 0 ? param.substring(index + 1) : "";
        paramMap.add(paramName, paramValue);
      }

      if (result instanceof DynaResource) {
        result.setQueryParameters(paramMap);
      } else {
        String renditionKind = paramMap.getFirst(Resource.RENDITION);
        Rendition rendition = renditionKind != null ? result.getRendition(renditionKind) : null;
        if (rendition != null) {
          rendition.setQueryParameters(paramMap);
          result = rendition;
        }
      }
    }
    return result;
  }

  public Resource getChild(String path) {
    Resource result = null;
    if (GWT.isClient()) {
      if (children != null) {
        for (Resource child : children) {
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
  public List<Resource> getChildren() {
    return children;
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

  public String getMimeType() {
    return mimeType;
  }

  public <T> T getNativeData() {
    return (T) nativeData;
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
  public MultivaluedMap<String, String> getQueryParameters() {
    if (queryParameters == null) {
      queryParameters = new MultivaluedHashMap<String, String>();
    }
    return queryParameters;
  }

  public Resource getRelationship(Property prop) {
    if (prop.getType() instanceof ResourceType) {
      Resource result = (Resource) getPropertyValue(prop.getPath());
      return result;
    } else {
      return null;
    }
  }

  public Rendition getRendition(String kind) {
    return getRenditions().get(kind);
  }

  @XmlTransient
  public Map<String, Rendition> getRenditions() {
    if (renditions == null) {
      renditions = new HashMap<String, Rendition>();
      for (String kind : getResourceType().getRenditionKinds()) {
        if (HOME.equals(kind)) {
          continue;
        }
        Rendition rendition = new Rendition();
        rendition.setParent(this);
        rendition.setPath(kind);
        rendition.setTitle(kind);
        renditions.put(kind, rendition);
      }
    }
    return renditions;
  }

  @XmlTransient
  public ResourceType<?> getResourceType() {
    return TYPE;
  }

  public ResourceService getService() {
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
      if (parent != null) {
        widget = parent.getResourceType().getWidget(getPath());
      }
      if (widget == null) {
        widget = getResourceType().getWidget(HOME);
      }
    }
    return widget;
  }

  public boolean hasChildren() {
    return childrenCount > 0;
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml"})
  public DataGraph<Resource> load() {
    doLoad();
    DataGraph<Resource> data = new DataGraph<Resource>();
    data.root = this;
    return data;
  }

  public void loadChildren() {
    List<Resource> list = getService().findChildren(this);
    for (Resource res : list) {
      addChild(res);
    }
  }

  public void readFrom(MultivaluedMap<String, String> params) {
    String path = params.getFirst(Resource.PATH);
    String title = params.getFirst(Resource.TITLE);
    if (path != null) {
      this.path = path;
    }
    if (title != null) {
      this.title = title;
    }
  }

  public void readFrom(Resource delta) {
    if (delta.title != null) {
      this.title = delta.title;
    }
    if (delta.path != null) {
      this.path = delta.path;
    }
  }

  public void removeChild(Resource resource) {
    if (children == null) {
      return;
    }
    children.remove(path);
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

  public void setPropertyValue(String name, String value) {
    if (TITLE.equals(name)) {
      title = value;
    } else if (PATH.equals(name)) {
      path = value;
    } else if (CHILDREN_COUNT.equals(name)) {
      childrenCount = value == null ? 0 : Long.valueOf(value);
    }
  }

  public void setQueryParameters(MultivaluedMap<String, String> queryParameters) {
    this.queryParameters = queryParameters;
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
    if (loadChildren) {
      loadChildren();
    }
  }
}
