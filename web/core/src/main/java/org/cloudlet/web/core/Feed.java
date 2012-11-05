package org.cloudlet.web.core;

import org.cloudlet.web.core.service.FeedService;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@MappedSuperclass
public abstract class Feed<E extends Entry> extends Content {

  private long totalResults;

  @Transient
  private List<E> entries = new ArrayList<E>();

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public E create(E child) {
    child.setParent(this);
    FeedService<Feed<E>, E> service = (FeedService<Feed<E>, E>) getService();
    return service.create(this, child);
  }

  @Override
  @Path("{path}")
  public <T extends Content> T getChild(@PathParam("path") String path) {
    Class<E> entryType = getEntryType();
    Content entry = getService().getChild(this, path, entryType);
    if (entry == null) {
      return super.getChild(path);
    } else {
      return (T) entry;
    }
  }

  public List<E> getEntries() {
    return entries;
  }

  public abstract Class<E> getEntryType();

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public long getTotalResults() {
    return totalResults;
  }

  @Override
  public Content load() {
    return load(0, -1);
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Content load(@QueryParam("start") @DefaultValue("0") int start,
      @QueryParam("limit") @DefaultValue("-1") int limit) {
    if (limit != 0) {
      List<E> entries = getService().findChildren(this, start, limit, getEntryType());
      setEntries(entries);
    }
    return this;
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  public void setTotalResults(long totalResults) {
    this.totalResults = totalResults;
  }

  @Override
  protected <T extends Content> T create(T child) {
    E entry = (E) child;
    return (T) create(entry);
  }

}
