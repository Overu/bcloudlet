package org.cloudlet.book.servlet;

import com.google.inject.servlet.ServletModule;

import java.util.logging.Logger;

public class BookServletModule extends ServletModule {

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Override
  protected void configureServlets() {
    logger.finest("installPersistModule begin");

    serve("/import").with(ImporterServlet.class);

    logger.finest("installPersistModule end");
  }

}
