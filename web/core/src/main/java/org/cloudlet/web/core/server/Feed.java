package org.cloudlet.web.core.server;

import com.google.gwt.core.shared.GWT;

import com.sencha.gxt.data.shared.SortInfo;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public abstract class Feed<E extends Resource> extends Resource {

  // sort=title|asc&sort=email|desc
  @QueryParam(CorePackage.SORT)
  @Transient
  protected List<String> sort;

  @QueryParam(CorePackage.SEARCH)
  @Transient
  protected List<String> search;

  @Transient
  protected List<E> entries;

  @Transient
  protected List<? extends SortInfo> sortInfo;

  @Transient
  protected Long queryCount;

  public long countEntries() {
    Class<E> entryType = getEntryType();
    TypedQuery<Long> query =
        em().createQuery("select count(f) from " + entryType.getName() + " f where f.parent=:parent" + count(), Long.class);
    query.setParameter("parent", this);
    return query.getSingleResult().longValue();
  }

  public E createEntry(E entry) {
    entry.setParent(this);
    entry.save();

    setChildrenCount(getChildrenCount() + 1);
    save();

    return entry;
  }

  public List<E> findEntries(int start, int limit) {
    Class<E> entryType = getEntryType();
    StringBuilder sql = new StringBuilder("from ").append(entryType.getName()).append(" f where f.parent=:parent");
    initQueryConditions(sql);
    sql.append(count()).append(limit());
    TypedQuery<E> query = em().createQuery(sql.toString(), entryType);
    if (start >= 0 && limit >= 0) {
      query.setFirstResult(start);
      query.setMaxResults(limit);
    }
    query.setParameter("parent", this);
    initQueryParams(query);
    return query.getResultList();
  }

  public E findEntry(String path) {
    try {
      Class<E> entryType = getEntryType();
      TypedQuery<E> query = em().createQuery("from " + entryType.getName() + " e where e.parent=:parent and e.path=:path", entryType);
      query.setParameter("parent", this);
      query.setParameter("path", path);
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
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
      result = findEntry(path);
    }
    if (result != null && resourceContext != null) {
      resourceContext.initResource(result);
    }
    return result;
  }

  @XmlTransient
  public abstract Class<E> getEntryType();

  @Override
  public Object getPropertyValue(String path) {
    return super.getPropertyValue(path);
  }

  @XmlElement
  public Long getQueryCount() {
    return queryCount;
  }

  @Path(CorePackage.NEW)
  @GET
  public E newEntry() {
    return create(getEntryType());
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
    entries = findEntries(0, -1);
    queryCount = countEntries();
  }

  protected void initQueryConditions(StringBuilder sql) {
  }

  protected void initQueryParams(TypedQuery<E> query) {
  }

  private StringBuffer count() {
    StringBuffer limitQuery = new StringBuffer();
    if (search != null && search.size() > 0) {
      limitQuery.append(" and (");
      for (String s : search) {
        String[] split = s.split("\\|");
        limitQuery.append("f.").append(split[0]).append(" like '").append(split[1]).append("%'").append(" or ");
      }
      limitQuery.delete(limitQuery.lastIndexOf(" or "), limitQuery.length());
      limitQuery.append(")");
    }
    return limitQuery;
  }

  private StringBuffer limit() {
    StringBuffer countQuery = new StringBuffer();
    if (sort != null && sort.size() > 0) {
      countQuery.append(" order by");
      for (String s : sort) {
        String[] split = s.split("\\|");
        countQuery.append(" f.").append(split[0]).append(" ").append(split[1]).append(",");
      }
      countQuery.deleteCharAt(countQuery.lastIndexOf(","));
    }
    return countQuery;
  }
}
