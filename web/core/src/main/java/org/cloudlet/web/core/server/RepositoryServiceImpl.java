package org.cloudlet.web.core.server;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.service.RepositoryService;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

@Singleton
public class RepositoryServiceImpl extends ServiceImpl<Repository> implements RepositoryService,
    Provider<Repository> {

  private static final Logger logger = Logger.getLogger(RepositoryServiceImpl.class.getName());

  @Override
  @Transactional
  public <CHILD extends org.cloudlet.web.core.Content> CHILD create(Repository parent, CHILD child) {
    return super.create(parent, child);
  }

  @Override
  public Repository get() {
    Repository repo;
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
