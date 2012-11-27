package org.cloudlet.web.core.service;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.web.core.Repository;

import javax.persistence.NoResultException;

@Singleton
public class RepositoryProvider implements Provider<RepositoryBean> {

  @Override
  public RepositoryBean get() {
    RepositoryBean repo = null;
    try {
      repo = WebPlatform.get().getEntityManager().createQuery("from " + Repository.TYPE, RepositoryBean.class).getSingleResult();
    } catch (NoResultException e) {
      repo = new RepositoryBean();
      WebPlatform.get().injectMembers(repo);
      repo.save();
    }
    return repo;
  }

}