package org.cloudlet.web.core;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.persistence.NoResultException;

@Singleton
public class RepositoryProvider implements Provider<Repository> {

  @Override
  public Repository get() {
    Repository repo = null;
    try {
      repo = WebPlatform.get().getEntityManager().createQuery("from " + Repository.TYPE, Repository.class).getSingleResult();
    } catch (NoResultException e) {
      repo = new Repository();
      WebPlatform.get().injectMembers(repo);
      repo.save();
    }
    return repo;
  }

}
