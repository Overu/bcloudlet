package org.cloudlet.web.core.service;

import com.google.gwt.core.shared.GWT;

import com.sencha.gxt.data.shared.SortInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
public abstract class FeedBean<E extends ResourceBean> extends ResourceBean {

  public static final String SORT = "sort";

  // sort=title|asc&sort=email|desc
  @QueryParam("sortby")
  @Transient
  protected String sortBy;

  @QueryParam("sortorder")
  @Transient
  protected String sortOrder;

  @Transient
  protected List<E> entries;

  @Transient
  protected List<? extends SortInfo> sortInfo;

  @Transient
  protected Long queryCount;

  public static final String NEW = "new";

  public static final String LIST = "list";

  public static final String ENTRIES = "entries";

  public long countEntries() {
    Class<E> entryType = getEntryType();
    TypedQuery<Long> query = em().createQuery("select count(o) from " + entryType.getName() + " o where o.parent=:parent", Long.class);
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
    String orderBy = sortBy == null ? "" : (" order by f." + sortBy + (sortOrder == null ? "" : " " + sortOrder));
    TypedQuery<E> query = em().createQuery("from " + entryType.getName() + " f where f.parent=:parent" + orderBy, entryType);
    if (start >= 0 && limit >= 0) {
      query.setFirstResult(start);
      query.setMaxResults(limit);
    }
    query.setParameter("parent", this);
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

  public E newEntry() {
    return create(getEntryType());
  }

  public void setEntries(List<E> entries) {
    this.entries = entries;
  }

  @Override
  protected ResourceBean doGetByPath(String path) {
    ResourceBean result = getEntry(path);
    if (result == null) {
      result = super.doGetByPath(path);
    }
    return result;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    if (NEW.equals(renditionKind)) {
      entries = new ArrayList<E>();
      E entry = newEntry();
      entries.add(entry);
    } else if (LIST.equals(renditionKind)) {
      doLoadEntries();
    } else {
      doLoadEntries();
    }
  }

  protected void doLoadEntries() {
    entries = findEntries(0, -1);
    queryCount = countEntries();
  }

}
