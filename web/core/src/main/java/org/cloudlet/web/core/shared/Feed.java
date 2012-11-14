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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType(name = Feed.TYPE_NAME)
public abstract class Feed<E extends Entry> extends Content {

  public static final String TYPE_NAME = "Feed";

  public static FeedType TYPE = new FeedType(Content.TYPE, TYPE_NAME, Entry.TYPE);

  @Transient
  protected List<E> entries;
  @Transient
  protected Long queryCount;

  public static final String POST_WIDGET = "post";

  public static final String LIST_WIDGET = "list";

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

  public List<E> getEntries() {
    return entries;
  }

  @Path("{path}")
  public final E getEntry(@PathParam("path") String path) {
    E result = (E) getCache().get(path);
    if (result == null && !GWT.isClient()) {
      result = (E) getService().findEntry(this, path);
      if (result != null) {
        getCache().put(path, result);
      }
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return result;
  }

  public abstract Class<E> getEntryType();

  @XmlElement
  public Long getQueryCount() {
    return queryCount;
  }

  @Override
  public FeedType getResourceType() {
    return TYPE;
  }

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    loadEntries();
  }

  protected void loadEntries() {
    FeedService service = getService();
    entries = service.findEntries(this, 0, -1);
    queryCount = service.countEntries(this);
  }

}
