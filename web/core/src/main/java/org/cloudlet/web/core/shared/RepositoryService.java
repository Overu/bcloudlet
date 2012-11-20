package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.RepositoryServiceImpl;

@ImplementedBy(RepositoryServiceImpl.class)
public interface RepositoryService extends ResourceService<Repository> {
}
