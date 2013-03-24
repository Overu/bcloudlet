package org.cloudlet.book.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import java.util.logging.Logger;

@Singleton
public class BookServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(BookServerModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    // bind(Repository.class).toProvider(BookStoreProvider.class);

    logger.finest("End configure " + getClass().getName());
  }
}