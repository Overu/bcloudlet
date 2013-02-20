package org.cloudlet.web.core.server;

import com.sencha.gxt.data.shared.SortInfo;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public abstract class Feed<E extends Entry> extends Content {

  @QueryParam("start")
  @DefaultValue("0")
  @Transient
  protected Integer start;

  @QueryParam("limit")
  @DefaultValue("-1")
  @Transient
  protected Integer limit;

  // sort=title|asc&sort=email|desc
  @QueryParam(CorePackage.SORT)
  @Transient
  protected List<String> sort;

  @QueryParam(CorePackage.SEARCH)
  @Transient
  protected List<String> search;

  @Transient
  protected List<? extends SortInfo> sortInfo;

  public E createEntry(E entry) {
    return (E) getService().createEntry(this, entry);
  }

  @Override
  @DELETE
  public void delete() {
    getService().deleteFeed(this);
  }

  public E getEntry(String path) {
    return (E) getService().getEntry(this, path);
  }

  @XmlTransient
  public abstract Class<E> getEntryType();

  public Integer getLimit() {
    return limit;
  }

  @Override
  public Object getPropertyValue(String path) {
    return super.getPropertyValue(path);
  }

  public List<String> getSearch() {
    return search;
  }

  @Override
  public FeedService getService() {
    return (FeedService) super.getService();
  }

  public List<String> getSort() {
    return sort;
  }

  public Integer getStart() {
    return start;
  }

  @Path(CorePackage.NEW)
  @GET
  public E newEntry() {
    return WebPlatform.get().getInstance(getEntryType());
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public void setSearch(List<String> search) {
    this.search = search;
  }

  public void setSort(List<String> sort) {
    this.sort = sort;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  @Override
  public Content update() {
    getService().update(this);
    return this;
  }

  @Override
  protected List<E> doLoad() {
    return getService().findEntries(this);
  }

  @Override
  protected Content findChild(String path) {
    return getEntry(path);
  }

  @Override
  protected void init() {
    getService().init(this);
  }
}
