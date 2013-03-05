package org.cloudlet.web.core.server;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Path;

public class FeedService<F extends Feed<E>, E extends Entry> extends EntryService<E> {

  private static final Logger logger = Logger.getLogger(FeedService.class.getName());

  protected final Class<F> feedClass;

  public FeedService(Class<F> feedClass, Class<E> entryClass) {
    super(entryClass);
    this.feedClass = feedClass;
  }

  public long countEntries(F parent) {
    StringBuilder sql = new StringBuilder("select count(f) from ").append(entryClass.getName());
    sql.append(" f where f.parent=:parent");
    parent.buildSearch(sql);
    TypedQuery<Long> query = em().createQuery(sql.toString(), Long.class);
    query.setParameter("parent", parent);
    long count = query.getSingleResult().longValue();
    return count;
  }

  public E createEntry(E entry) {
    return (E) createChild(getRoot(), entry);
  }

  public E createEntry(F parent, E entry) {
    return (E) createChild(parent, entry);
  }

  public void deleteFeed(F bean) {
    em().remove(bean);
  }

  public List<E> findEntries(F feed) {
    StringBuilder sql = new StringBuilder("from ").append(entryClass.getName()).append(" f");
    sql.append(" where f.parent=:parent");
    feed.prepareQuery(sql);
    feed.buildSearch(sql);
    feed.buildSort(sql);

    TypedQuery<E> query = em().createQuery(sql.toString(), entryClass);
    if (feed.getStart() != null) {
      query.setFirstResult(feed.getStart());
    }
    if (feed.getLimit() != null && feed.getLimit() > 0) {
      query.setMaxResults(feed.getLimit());
    }
    query.setParameter("parent", feed);
    feed.setParams(query);
    return query.getResultList();
  }

  public E getEntry(F parent, String path) {
    try {
      TypedQuery<E> query = em().createQuery("from " + entryClass.getName() + " e where e.parent=:parent and e.path=:path", entryClass);
      query.setParameter("parent", parent);
      query.setParameter("path", path);
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public F getRoot() {
    Path p = feedClass.getAnnotation(Path.class);
    String path = p.value();
    Repository repo = repositoryService.getRoot();
    F result = (F) repo.getChild(path);
    if (result == null) {
      result = WebPlatform.get().getInstance(feedClass);
      result.setPath(path);
      if (result.getTitle() == null) {
        result.setTitle(path);
      }
      repo.createReference(result);
    }
    return result;
  }

  public void update(F feed) {
    super.update(feed);
  }

  protected void init(F feed) {
  }

}
