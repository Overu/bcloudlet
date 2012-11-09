package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.server.BaseRepositoryServiceImpl;

public class BaseRepositoryTest extends RepositoryTest<Repository> {

  protected Class<? extends RepositoryService<Repository>> getRepositoryProvider() {
    return BaseRepositoryServiceImpl.class;
  }

}
