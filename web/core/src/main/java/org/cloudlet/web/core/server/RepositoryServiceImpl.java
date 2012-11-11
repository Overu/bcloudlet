package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.RepositoryService;

import javax.persistence.NoResultException;

@Singleton
public class RepositoryServiceImpl extends EntryServiceImpl<Repository> implements
    RepositoryService {

  @Override
  public Repository get() {
    Repository repo = null;
    try {
      repo =
          em().createQuery("from " + Repository.class.getName(), Repository.class)
              .getSingleResult();
    } catch (NoResultException e) {
      repo = new Repository();
      repo = save(repo);
    }
    return repo;
  }

}
