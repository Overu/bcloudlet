package org.cloudlet.web.core.shared;

import com.google.gwt.core.shared.GWT;

import com.sencha.gxt.data.shared.SortInfo;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType(name = Feed.TYPE_NAME)
public abstract class Feed<E extends Resource> extends Resource {

  public static final String TYPE_NAME = "Feed";

  public static FeedType TYPE = new FeedType(Resource.TYPE, TYPE_NAME, Resource.TYPE);

  public static final String SORT = "sort";

  @Transient
  protected List<E> entries;

  @Transient
  protected List<? extends SortInfo> sortInfo;

  @Transient
  protected Long queryCount;

  public static final String NEW = "new";

  public static final String LIST = "list";

  public static final String ENTRIES = "entries";

  @Override
  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public DataGraph<Resource> create(DataGraph<Resource> data) {
    if (!getResourceType().getEntryType().isInstance(data.root)) {
      // does not accept an entry of given type
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    data.root = createEntry((E) data.root);
    return data;
  }

  public E createEntry(E entry) {
    return (E) getService().createEntry(this, entry);
  }

  @XmlElement
  public List<E> getEntries() {
    return entries;
  }

  public E getEntry(String path) {
    E result = null;
    if (GWT.isClient()) {
      if (entries != null) {
        for (E entry : entries) {
          if (path.equals(entry.getPath())) {
            result = entry;
          }
        }
      }
    } else {
      result = (E) getService().findEntry(this, path);
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return result;
  }

  public abstract Class<E> getEntryType();

  @Override
  public Object getPropertyValue(String path) {
    return super.getPropertyValue(path);
  }

  @XmlElement
  public Long getQueryCount() {
    return queryCount;
  }

  @Override
  public FeedType<? extends Feed<?>, ? extends E> getResourceType() {
    return TYPE;
  }

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public E newEntry() {
    return getResourceType().getEntryType().createInstance();
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  @Override
  protected Resource doGetByPath(String path) {
    Resource result = getEntry(path);
    if (result == null) {
      result = super.doGetByPath(path);
    }
    return result;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    doLoadEntries();
  }

  protected void doLoadEntries() {
    entries = getService().findEntries(this, 0, -1);
    queryCount = getService().countEntries(this);
  }

}
