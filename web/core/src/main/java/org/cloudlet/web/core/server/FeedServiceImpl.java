package org.cloudlet.web.core.server;

import org.cloudlet.web.core.Entry;
import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.service.FeedService;

import java.util.List;

import javax.persistence.TypedQuery;

public class FeedServiceImpl<F extends Feed<E>, E extends Entry> extends ServiceImpl<F> implements
    FeedService<F, E> {

  @Override
  public <CHILD extends org.cloudlet.web.core.Content> CHILD create(F parent, CHILD child) {
    CHILD result = super.create(parent, child);
    parent.setTotalResults(parent.getTotalResults() + 1);
    parent.update();
    return result;
  }

  @Override
  public List<E> findChildren(F parent, int start, int limit, Class<E> entryType) {
    TypedQuery<E> query =
        em().createQuery("from " + entryType.getName() + " f where f.parent=:parent", entryType);
    if (start >= 0 && limit >= 0) {
      query.setFirstResult(start);
      query.setMaxResults(limit);
    }
    query.setParameter("parent", parent);
    return query.getResultList();
  }

}
