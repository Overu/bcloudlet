package org.cloudlet.core.server;

import com.google.inject.Provider;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public abstract class RepositoryProvider<T extends Repository> implements Provider<T> {

  @Override
  public T get() {
    EntityManager em = WebPlatform.get().getEntityManager();
    Class<T> type = getType();
    T repo = null;
    try {
      repo = em.createQuery("from " + type.getName(), type).getSingleResult();
    } catch (NoResultException e) {
      repo = CoreUtil.newInstance(type);
      repo.save();
    }
    return repo;
  }

  public abstract Class<T> getType();
}
