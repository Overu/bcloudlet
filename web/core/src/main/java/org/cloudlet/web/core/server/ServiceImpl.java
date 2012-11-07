package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import org.cloudlet.web.core.Content;
import org.cloudlet.web.core.service.Service;

import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ServiceImpl<T extends Content> implements Service<T> {

  private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

  @Inject
  private Provider<EntityManager> entityManagerProvider;

  @Override
  @Transactional
  public void delete(T content) {
    em().remove(content);
  }

  @Override
  public T getById(String id, Class<T> type) {
    return em().find(type, id);
  }

  @Override
  public <CHILD extends Content> CHILD getChild(T parent, String path, Class<CHILD> childType) {
    TypedQuery<CHILD> query =
        em().createQuery(
            "from " + childType.getName() + " f where f.parent=:parent and f.path=:path", childType);
    query.setParameter("parent", parent);
    query.setParameter("path", path);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public T save(T content) {
    if (content.getId() == null) {
      content.setId(UUID.randomUUID().toString());
    }
    em().persist(content);

    return content;
  }

  @Override
  @Transactional
  public T update(T content) {
    em().persist(content);
    return content;
  }

  @Transactional
  protected <CHILD extends Content> CHILD create(T parent, CHILD child) {
    child.setParent(parent);
    if (child.getId() == null) {
      child.setId(UUID.randomUUID().toString());
    }
    if (child.getPath() == null) {
      child.setPath(child.getId());
    }
    em().persist(child);
    return child;
  }

  protected EntityManager em() {
    return entityManagerProvider.get();
  }

}
