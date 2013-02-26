package org.cloudlet.web.core.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;

import java.util.logging.Logger;

@Singleton
public class WebServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(WebServerModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    install(new JpaPersistModule("persist.jpaUnit")); // TODO read from

    requestStaticInjection(InjectionListener.class);

    bind(WebPlatform.class).asEagerSingleton();
    // bind(Repository.class).toProvider(RepositoryService.class);
    // bind(UserFeed.class).toProvider(UserFeedProvider.class);
    // bind(GroupFeed.class).toProvider(GroupFeedProvider.class);
    // bind(BookFeed.class).toProvider(BookFeedService.class);

    // MethodInterceptor finderInterceptor = new JpaFinderProxy();
    // requestInjection(finderInterceptor);
    // bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }
}