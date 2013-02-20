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
    TypedQuery<Long> query =
        em().createQuery("select count(f) from " + entryClass.getName() + " f where f.parent=:parent" + count(parent.getSearch()),
            Long.class);
    query.setParameter("parent", parent);
    long count = query.getSingleResult().longValue();
    parent.setTotalChildren(count);
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
    Class<E> entryType = feed.getEntryType();
    StringBuilder sql = new StringBuilder("from ").append(entryType.getName()).append(" f where f.parent=:parent");
    initQueryConditions(feed, sql);
    sql.append(count(feed.getSearch())).append(limit(feed.getSort()));
    TypedQuery<E> query = em().createQuery(sql.toString(), entryType);
    if (feed.getStart() != null) {
      query.setFirstResult(feed.getStart());
    }
    if (feed.getLimit() != null && feed.getLimit() > 0) {
      query.setMaxResults(feed.getLimit());
    }
    query.setParameter("parent", feed);
    initQueryParams(feed, query);
    return query.getResultList();
  }

  public E getEntry(F parent, String path) {
    try {
      Class<E> entryType = parent.getEntryType();
      TypedQuery<E> query = em().createQuery("from " + entryType.getName() + " e where e.parent=:parent and e.path=:path", entryType);
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

  protected void initQueryConditions(F feed, StringBuilder sql) {
  }

  protected void initQueryParams(F feed, TypedQuery<E> query) {
  }

  private StringBuffer count(List<String> search) {
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

  private StringBuffer limit(List<String> sort) {
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
