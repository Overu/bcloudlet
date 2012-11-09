package org.cloudlet.web.core;

import org.cloudlet.web.core.service.FeedService;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@MappedSuperclass
@XmlType
public abstract class Feed<E extends Entry> extends Content {

  @Transient
  protected List<E> entries;

  @Transient
  protected Long queryCount;

  public E createEntry(E entry) {
    return (E) getService().createEntry(this, entry);
  }

  public List<E> getEntries() {
    return entries;
  }

  @Path("{path}")
  public final E getEntry(@PathParam("path") String path) {
    E result = (E) getCache().get(path);
    if (result == null) {
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
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  @Override
  protected Content createChild(Content child) {
    if (!getEntryType().isInstance(child)) {
      // does not accept an entry of given type
      throw new WebApplicationException(Status.BAD_REQUEST);
    }
    return createEntry((E) child);
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
