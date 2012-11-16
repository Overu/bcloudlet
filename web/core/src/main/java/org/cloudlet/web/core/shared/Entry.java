package org.cloudlet.web.core.shared;

import com.google.gwt.core.shared.GWT;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType(name = Entry.TYPE_NAME)
public abstract class Entry extends Resource {

  public static final String TYPE_NAME = "Entry";

  public static EntryType<Entry> TYPE = new EntryType<Entry>(Resource.TYPE, TYPE_NAME);

  public static final String CHILDREN = "children";

  @Transient
  private List<Resource> children;

  @Transient
  @QueryParam(CHILDREN)
  protected boolean loadChildren;

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

  @Path("{path}")
  public <T extends Resource> T getChild(@PathParam("path") String path) {
    Resource result = getCache().get(path);
    if (result == null && !GWT.isClient()) {
      result = getService().getChild(this, path);
      if (result != null) {
        getCache().put(path, result);
      }
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return (T) result;
  }

  public <T extends Resource> T getChild(String path, Class<T> childType) {
    Resource result = getCache().get(path);
    if (result == null && !GWT.isClient()) {
      result = getService().findChild(this, path, childType);
      if (result != null) {
        getCache().put(path, result);
      }
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return (T) result;
  }

  @XmlElement
  public List<Resource> getChildren() {
    return children;
  }

  public <T extends Resource> List<T> getChildren(Class<T> childType) {
    return getService().findChildren(this, childType);
  }

  @Override
  public EntryType<?> getResourceType() {
    return TYPE;
  }

  @Override
  public EntryService getService() {
    return (EntryService) super.getService();
  }

  public void laodChildren() {
    children = getService().findChildren(this);
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    if (loadChildren) {
      laodChildren();
    }
  }
}
