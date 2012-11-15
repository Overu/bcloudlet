package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;

import org.cloudlet.web.core.server.ResourceEntity;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.io.InputStream;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = "content", typeClass = ResourceEntity.class)
@MappedSuperclass
@XmlType(name = Resource.TYPE_NAME)
public abstract class Resource extends Place {

  public static final String TYPE_NAME = "Resource";

  private static final Logger logger = Logger.getLogger(Resource.class.getName());

  public static ResourceType TYPE = new ResourceType(TYPE_NAME);

  public static final String ID = "id";

  public static final String TITLE = "title";

  public static final String PATH = "path";

  public static final String URI = "uri";

  public static final String VERSION = "version";

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
  private Content parent;

  @Transient
  private transient Object nativeData;

  @Transient
  private transient InputStream inputStream;

  @DELETE
  public void delete() {
    getService().delete(this);
  }

  public String getId() {
    return id;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public Object getNativeData() {
    return nativeData;
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
    return widget;
  }

  // public abstract boolean isLoaded();

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

  public void setId(final String id) {
    this.id = id;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public void setNativeData(Object nativeData) {
    this.nativeData = nativeData;
  }

  public void setOwner(final User owner) {
    this.owner = owner;
  }

  public void setParent(final Content parent) {
    this.parent = parent;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public void setProperty(String name, String value) {
    if (TITLE.equals(name)) {
      title = value;
    } else if (PATH.equals(name)) {
      path = value;
    }
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
