package org.cloudlet.web.core.server;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.aopalliance.intercept.MethodInterceptor;
import org.cloudlet.web.core.WebPlatform;
import org.cloudlet.web.core.WebServerPlatform;

import java.util.logging.Logger;

@Singleton
public class WebServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(WebServerModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    install(new JpaPersistModule("persist.jpaUnit")); // TODO read from

    bind(WebPlatform.class).to(WebServerPlatform.class).asEagerSingleton();

    MethodInterceptor finderInterceptor = new JpaFinderProxy();
    requestInjection(finderInterceptor);
    bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }
}