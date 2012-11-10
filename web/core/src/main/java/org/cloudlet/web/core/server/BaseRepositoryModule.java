package org.cloudlet.web.core.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Repository;

import java.util.logging.Logger;

@Singleton
public class BaseRepositoryModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(BaseRepositoryModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.info("Configure " + getClass().getName());
    bind(Repository.class).toProvider(BaseRepositoryServiceImpl.class);
  }
}