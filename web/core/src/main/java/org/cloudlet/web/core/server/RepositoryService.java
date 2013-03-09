package org.cloudlet.web.core.server;

import com.google.inject.Singleton;


import javax.persistence.NoResultException;

@Singleton
public class RepositoryService extends EntryService<Repository> {

  public RepositoryService() {
    super(Repository.class);
  }

  public Repository getRoot() {
    Repository repo = null;
    try {
      repo = em().createQuery("from " + Repository.TYPE_NAME, Repository.class).getSingleResult();
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
    users.setPath(Repository.USERS);
    users.setTitle("Users");
    createReference(repo, users);

    Books books = new Books();
    books.setPath(Repository.BOOKS);
    books.setTitle("Books");
    createReference(repo, books);

    Tags tags = new Tags();
    tags.setPath(Repository.TAGS);
    tags.setTitle("Tags");
    createReference(repo, tags);

    repo.setUsers(users);
    repo.setBooks(books);
    repo.setTags(tags);
    update(repo);
  }
}
