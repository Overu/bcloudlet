package org.cloudlet.web.core.server;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.NoResultException;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
@EntityListeners(InjectionListener.class)
public abstract class Entry extends Content {

  private static final Logger logger = Logger.getLogger(Entry.class.getName());

  public static final String REFERENCES = "children";

  @Transient
  @QueryParam(Entry.REFERENCES)
  protected boolean loadReferences;

  @Transient
  protected List<Reference> references;

  @Transient
  protected Long count;

  public long countReferences() {
    TypedQuery<Long> query =
        em().createQuery("select count(ref) from " + Reference.class.getName() + " ref where ref.source=:source", Long.class);
    query.setParameter("source", this);
    long count = query.getSingleResult().longValue();
    return count;
  }

  public Content createReference(Content target) {
    final Reference ref = new Reference();
    ref.setId(CoreUtil.randomID());
    ref.setSource(this);
    ref.setTarget(target);
    ref.setPath(target.getPath());
    if (!em().contains(target)) {
      createChild(target);
      ref.setContaiment(true);
    }
    em().persist(ref);
    return target;
  }

  public List<Reference> findReferences() {
    TypedQuery<Reference> query = em().createQuery("from " + Reference.class.getName() + " ref where ref.source=:source", Reference.class);
    query.setParameter("source", this);
    return query.getResultList();
  }

  public Long getCount() {
    return count;
  }

  public Content getReference(String path) {
    try {
      TypedQuery<Reference> query =
          em().createQuery("from " + Reference.class.getName() + " rel where rel.source=:source and rel.path=:path", Reference.class);
      query.setParameter("source", this);
      query.setParameter("path", path);
      return query.getSingleResult().getTarget();
    } catch (NoResultException e) {
      return null;
    }
  }

  @XmlElement
  public List<Reference> getReferences() {
    return references;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setReferences(List<Reference> refs) {
    this.references = refs;
  }

  @Override
  protected void doLoad() {
    if (loadReferences) {
      references = findReferences();
      count = countReferences();
    }
  }

  @Override
  protected Content findChild(String path) {
    return getReference(path);
  }

}
