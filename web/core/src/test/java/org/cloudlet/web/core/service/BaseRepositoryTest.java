package org.cloudlet.web.core.service;

import org.cloudlet.web.core.server.BaseRepositoryServiceImpl;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.RepositoryService;

public class BaseRepositoryTest extends RepositoryTest<Repository> {

  protected Class<? extends RepositoryService<Repository>> getRepositoryProvider() {
    return BaseRepositoryServiceImpl.class;
  }

}
