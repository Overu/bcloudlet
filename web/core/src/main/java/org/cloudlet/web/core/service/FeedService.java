package org.cloudlet.web.core.service;

import com.google.inject.persist.Transactional;

import org.cloudlet.web.core.bean.FeedBean;
import org.cloudlet.web.core.bean.ResourceBean;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class FeedService<F extends FeedBean<E>, E extends ResourceBean> extends ResourceService<F> {

  /**
   * @param resourceClass
   */
  public FeedService(Class<F> cls) {
    super(cls);
  }

  public long countEntries(F feed) {
    Class<E> entryType = feed.getEntryType();
    TypedQuery<Long> query =
        em().createQuery(
            "select count(o) from " + entryType.getName() + " o where o.parent=:parent", Long.class);
    query.setParameter("parent", feed);
    return query.getSingleResult().longValue();
  }

  @Transactional
  public E createEntry(F feed, E entry) {
    // check if child path conflicts
    if (entry.getPath() != null && findEntry(feed, entry.getPath()) != null) {
      throw new EntityExistsException("A child with path=" + entry.getPath() + " already exists");
    }
    entry.setParent(feed);
    entry.save();

    feed.setChildrenCount(feed.getChildrenCount() + 1);
    update(feed);

    return entry;
  }

  public List<E> findEntries(F parent, int start, int limit) {
    Class<E> entryType = parent.getEntryType();
    TypedQuery<E> query =
        em().createQuery("from " + entryType.getName() + " f where f.parent=:parent", entryType);
    if (start >= 0 && limit >= 0) {
      query.setFirstResult(start);
      query.setMaxResults(limit);
    }
    query.setParameter("parent", parent);
    return query.getResultList();
  }

  public E findEntry(F parent, String path) {
    try {
      Class<E> entryType = parent.getEntryType();
      TypedQuery<E> query =
          em().createQuery(
              "from " + entryType.getName() + " e where e.parent=:parent and e.path=:path",
              entryType);
      query.setParameter("parent", parent);
      query.setParameter("path", path);
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}