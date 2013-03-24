package org.cloudlet.core.server;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Singleton
public class RepositoryProvider implements Provider<Repository> {

  @Override
  public Repository get() {
    EntityManager em = WebPlatform.get().getEntityManager();
    Repository repo = null;
    try {
      repo = em.createQuery("from " + Repository.TYPE_NAME, Repository.class).getSingleResult();
    } catch (NoResultException e) {
      repo = new Repository();
      repo.save();
    }
    return repo;
  }
}
