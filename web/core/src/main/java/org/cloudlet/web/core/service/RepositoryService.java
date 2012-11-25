package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.RepositoryBean;

import javax.persistence.NoResultException;

@Singleton
public class RepositoryService extends ResourceService<RepositoryBean> {

  public RepositoryService() {
    super(RepositoryBean.class);
  }

  @Override
  public RepositoryBean get() {
    RepositoryBean repo = null;
    try {
      repo =
          em().createQuery("from " + RepositoryBean.class.getName(), RepositoryBean.class)
              .getSingleResult();
    } catch (NoResultException e) {
      repo = new RepositoryBean();
      repo = save(repo);
    }
    return repo;
  }

}
