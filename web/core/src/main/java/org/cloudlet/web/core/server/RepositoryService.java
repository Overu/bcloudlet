package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.NoResultException;

@Singleton
public class RepositoryService extends EntryService<Repository> {

  public RepositoryService() {
    super(Repository.class);
  }

  public Repository getRoot() {
    Repository repo = null;
    try {
      repo = em().createQuery("from " + CorePackage.Repository, Repository.class).getSingleResult();
    } catch (NoResultException e) {
      repo = new Repository();
      createReference(null, repo);
    }
    return repo;
  }

  @Override
  protected void init(Repository repo) {
    super.init(repo);
    Users users = new Users();
    users.setPath("users");
    users.setTitle("Users");
    createReference(repo, users);

    Books books = new Books();
    books.setPath(CorePackage.BOOKS);
    books.setTitle("Books");
    createReference(repo, books);

    repo.setUsers(users);
    repo.setBooks(books);
    update(repo);
  }
}
