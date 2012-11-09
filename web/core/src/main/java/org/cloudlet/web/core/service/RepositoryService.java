package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.server.BaseRepositoryServiceImpl;

@ImplementedBy(BaseRepositoryServiceImpl.class)
public interface RepositoryService<T extends Repository> extends EntryService<T> {
}
