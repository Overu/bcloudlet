package org.cloudlet.web.core.server;

import com.google.inject.persist.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class EntryService<E extends Entry> extends Service {

  private static final Logger logger = Logger.getLogger(EntryService.class.getName());

  protected final Class<E> entryClass;

  public EntryService(Class<E> entryClass) {
    this.entryClass = entryClass;
  }

  public long countReferences(E source) {
    TypedQuery<Long> query =
        em().createQuery("select count(ref) from " + Reference.class.getName() + " ref where ref.source=:source", Long.class);
    query.setParameter("source", source);
    long count = query.getSingleResult().longValue();
    return count;
  }

  @Transactional
  public Content createReference(E source, Content target) {
    final Reference reference = new Reference();
    reference.setId(CoreUtil.randomID());
    reference.setSource(source);
    reference.setTarget(target);
    reference.setPath(target.getPath());

    if (!em().contains(target)) {
      createChild(source, target);
      reference.setContaiment(true);
    }

    em().persist(reference);
    return target;
  }

  public void delete(E entry) {
    em().remove(entry);
  }

  public List<Content> findReferences(E source) {
    TypedQuery<Reference> query = em().createQuery("from " + Reference.class.getName() + " ref where ref.source=:source", Reference.class);
    query.setParameter("source", source);
    List<Reference> references = query.getResultList();
    List<Content> children = new ArrayList<Content>(references.size());
    for (Reference ref : references) {
      children.add(ref.getTarget());
    }
    return children;
  }

  public Content getReference(E source, String path) {
    try {
      TypedQuery<Reference> query =
          em().createQuery("from " + Reference.class.getName() + " rel where rel.source=:source and rel.path=:path", Reference.class);
      query.setParameter("source", source);
      query.setParameter("path", path);
      return query.getSingleResult().getTarget();
    } catch (NoResultException e) {
      return null;
    }
  }

  public void update(E entry) {
    super.update(entry);
  }

  protected void init(E entry) {
  }

}
