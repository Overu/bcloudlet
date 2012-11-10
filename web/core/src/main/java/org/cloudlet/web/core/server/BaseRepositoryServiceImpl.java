package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Repository;

@Singleton
public class BaseRepositoryServiceImpl extends RepositoryServiceImpl<Repository> {
  @Override
  protected Class<Repository> getRepositoryType() {
    return Repository.class;
  }
}