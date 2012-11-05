package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.Content;
import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.server.RepositoryServiceImpl;

@ImplementedBy(RepositoryServiceImpl.class)
public interface RepositoryService extends Service<Repository> {
  <CHILD extends Content> CHILD create(Repository parent, CHILD child);

}
