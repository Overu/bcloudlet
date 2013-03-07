package org.cloudlet.web.core.server;

import com.sencha.gxt.data.shared.SortInfo;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
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

  @Transient
  protected List<E> entries;

  protected long totalEntries;

  public void buildSearch(StringBuilder sql) {
    if (search != null && search.size() > 0) {
      sql.append(" and (");
      for (String s : search) {
        String[] split = s.split("\\|");
        sql.append("f.").append(split[0]).append(" like '").append(split[1]).append("%'").append(" or ");
      }
      sql.delete(sql.lastIndexOf(" or "), sql.length());
      sql.append(")");
    }
  }

  public void buildSort(StringBuilder sql) {
    if (sort != null && sort.size() > 0) {
      sql.append(" order by");
      for (String s : sort) {
        String[] split = s.split("\\|");
        sql.append(" f.").append(split[0]).append(" ").append(split[1]).append(",");
      }
      sql.deleteCharAt(sql.lastIndexOf(","));
    }
  }

  public E createEntry(E entry) {
    return (E) getService().createEntry(this, entry);
  }

  @Override
  @DELETE
  public void delete() {
    getService().delete(this);
  }

  public List<E> getEntries() {
    return entries;
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

  public long getTotalEntries() {
    return totalEntries;
  }

  @Path(CorePackage.NEW)
  @GET
  public E newEntry() {
    return WebPlatform.get().getInstance(getEntryType());
  }

  public void prepareQuery(StringBuilder sql) {
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public void setParams(TypedQuery<E> query) {
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

  public void setTotalEntries(long total) {
    this.totalEntries = total;
  }

  @Override
  public Content update() {
    getService().update(this);
    return this;
  }

  @Override
  protected void doLoad() {
    entries = getService().findEntries(this);
    totalEntries = getService().countEntries(this);
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
