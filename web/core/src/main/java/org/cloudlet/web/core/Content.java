package org.cloudlet.web.core;

import org.cloudlet.web.core.server.ContentType;
import org.cloudlet.web.core.service.Service;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = "content", typeClass = ContentType.class)
@MappedSuperclass
@XmlType
public abstract class Content {

  public static final String TITLE = "title";

  public static final String PATH = "path";

  protected String title;

  protected long totalCount;

  @Context
  @Transient
  UriInfo uriInfo;

  @Context
  @Transient
  ResourceContext resourceContext;

  @Id
  protected String id;

  protected String path;

  @Version
  protected Long version;

  @ManyToOne
  protected User owner;

  @Type(type = "content")
  @Columns(columns = {@Column(name = "parentType"), @Column(name = "parentId")})
  private Content parent;

  @Transient
  private Map<String, Content> cache;

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Content> create(DataGraph<Content> data) {
    data.root = createChild(data.root);
    return data;
  }

  @DELETE
  public void delete() {
    getService().delete(this);
  }

  public Map<String, Content> getCache() {
    if (cache == null) {
      cache = new HashMap<String, Content>();
    }
    return cache;
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

  public Object getProperty(String name) {
    if (TITLE.equals(name)) {
      return title;
    }
    if (PATH.equals(name)) {
      return path;
    }
    return null;
  }

  public Service getService() {
    return WebPlatform.getInstance().getService(getServiceType(getClass()));
  }

  public <T extends Service> Class<T> getServiceType(Class<? extends Content> contentClass) {
    Handler handler = contentClass.getAnnotation(Handler.class);
    if (handler != null) {
      return (Class<T>) handler.value();
    }
    Class superClass = contentClass.getSuperclass();
    if (Content.class.isAssignableFrom(superClass)) {
      return getServiceType(superClass);
    }
    // TODO proxy-based creation
    return null;
  }

  public String getTitle() {
    return title;
  }

  public long getTotalCount() {
    return totalCount;
  }

  @XmlElement
  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    if (parent == null) {
      return new StringBuilder("/");
    }
    StringBuilder builder = parent.getUriBuilder();
    builder.append(path).append("/");
    return builder;
  }

  public Long getVersion() {
    return version;
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Content> load() {
    doLoad();
    DataGraph<Content> data = new DataGraph<Content>();
    data.root = this;
    return data;
  }

  public Content save() {
    return getService().save(this);
  }

  public void setId(final String id) {
    this.id = id;
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

  public void setTotalCount(long totalResults) {
    this.totalCount = totalResults;
  }

  public void setVersion(final Long version) {
    this.version = version;
  }

  public Content update() {
    return getService().update(this);
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Content> update(DataGraph<Content> data) {
    readFrom(data.root);
    update();
    data.root = this;
    return data;
  }

  protected abstract Content createChild(Content child);

  protected void doLoad() {
  }

  protected void readFrom(Content delta) {
    if (delta.title != null) {
      this.title = delta.title;
    }
    if (delta.path != null) {
      this.path = delta.path;
    }
  }

}
