package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import org.cloudlet.web.core.Content;
import org.cloudlet.web.core.service.Service;

import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

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
  @Transactional
  public T save(T content) {
    if (content.getId() == null) {
      content.setId(UUID.randomUUID().toString());
    }
    if (content.getPath() == null) {
      content.setPath(content.getId());
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

  protected EntityManager em() {
    return entityManagerProvider.get();
  }

}
