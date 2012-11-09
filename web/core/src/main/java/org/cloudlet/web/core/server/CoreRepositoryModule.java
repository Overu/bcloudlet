package org.cloudlet.web.core.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import org.cloudlet.web.core.CoreRepository;
import org.cloudlet.web.core.Repository;

import java.util.logging.Logger;

@Singleton
public class CoreRepositoryModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(CoreRepositoryModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.info("Configure " + getClass().getName());
    bind(Repository.class).toProvider(CoreRepositoryServiceImpl.class);
    bind(CoreRepository.class).toProvider(CoreRepositoryServiceImpl.class);
  }
}