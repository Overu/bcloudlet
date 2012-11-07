package org.cloudlet.web.core;

import org.cloudlet.web.core.server.ContentType;
import org.cloudlet.web.core.service.Service;
import org.cloudlet.web.core.shared.CorePackage;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLStreamReader;

@TypeDef(name = "content", typeClass = ContentType.class)
@MappedSuperclass
@XmlType
public abstract class Content {

  protected String title;

  @Transient
  @QueryParam(CorePackage.Content.CHILDREN)
  protected boolean loadChildren;

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

  @Transient
  private List<Content> children;

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

  @Path("{path}")
  public <T extends Content> T getChild(@PathParam("path") String path) {
    Content result = getCache().get(path);
    if (result == null) {
      for (Method m : getClass().getMethods()) {
        Path p = m.getAnnotation(Path.class);
        if (p != null && p.value().equals(path)) {
          Class<?> rt = m.getReturnType();
          if (Content.class.isAssignableFrom(rt)) {
            Class<Content> childType = (Class<Content>) rt;
            try {
              result = getService().getChild(this, path, childType);
            } catch (NoResultException e) {
              try {
                result = childType.newInstance();
              } catch (InstantiationException e1) {
                e1.printStackTrace();
              } catch (IllegalAccessException e1) {
                e1.printStackTrace();
              }
              result.setPath(path);
              Relation rel = m.getAnnotation(Relation.class);
              if (rel != null) {
                result.setTitle(rel.value());
              }
              result = create(result);
            }
            getCache().put(path, result);
            break;
          } else {
            // TODO log exception
          }
        }
      }
    }
    if (resourceContext != null) {
      resourceContext.initResource(result);
    }
    return (T) result;
  }

  @XmlElement
  public List<Content> getChildren() {
    return children;
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

  public Service getService() {
    return WebPlatform.getInstance().getSerivce(getServiceType(getClass()));
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

  @XmlElement
  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    if (parent == null) {
      return new StringBuilder();
    }
    StringBuilder builder = parent.getUriBuilder();
    builder.append("/").append(path);
    return builder;
  }

  public Long getVersion() {
    return version;
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Content load() {
    loadBasicInfo();
    return this;
  }

  public void loadChildren() {
    children = new ArrayList<Content>();
    for (Method m : getClass().getMethods()) {
      Path p = m.getAnnotation(Path.class);
      if (p != null && !p.value().contains("{")) {
        Content child = getChild(p.value());
        children.add(child);
      }
    }
  }

  public void read(JSONObject json) {
    // TODO
  }

  public void read(XMLStreamReader xml) {
    // TODO
  }

  public <T extends Content> T save() {
    if (parent == null) {
      return (T) getService().save(this);
    } else {
      return (T) parent.create(this);
    }
  }

  //
  // @POST
  // @Consumes(MediaType.APPLICATION_JSON)
  // public Content create(JSONObject json) {
  // Content child = null; // TODO new conent;
  // child.read(json);
  // return create(child);
  // }
  //
  // @POST
  // @Consumes(MediaType.APPLICATION_XML)
  // public Content create(XMLStreamReader xml) {
  // Content child = null; // TODO new conent;
  // child.read(xml);
  // return create(child);
  // }

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

  public void setTitle(String title) {
    this.title = title;
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
  public Content update(JSONObject json) {
    read(json);
    return update();
  }

  protected abstract <T extends Content> T create(T child);

  protected void loadBasicInfo() {
    if (loadChildren) {
      loadChildren();
    }
  }

}
