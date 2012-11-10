package org.cloudlet.web.core.server;

import com.google.inject.Provider;

import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.RepositoryService;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

public abstract class RepositoryServiceImpl<T extends Repository> extends EntryServiceImpl<T>
    implements RepositoryService<T>, Provider<T> {

  private static final Logger logger = Logger.getLogger(RepositoryServiceImpl.class.getName());

  @Override
  public T get() {
    T repo = null;
    try {
      repo =
          em().createQuery("from " + getRepositoryType().getName(), getRepositoryType())
              .getSingleResult();
    } catch (NoResultException e) {
      try {
        repo = getRepositoryType().newInstance();
        repo = save(repo);
      } catch (Exception e1) {
        logger.severe("Failed to intialize repository.\r\n" + e1.getMessage());
      }
    }
    return repo;
  }

  protected abstract Class<T> getRepositoryType();

}
