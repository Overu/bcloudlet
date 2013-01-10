package org.cloudlet.web.core.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.cloudlet.web.core.BookFeed;
import org.cloudlet.web.core.BookFeedProvider;
import org.cloudlet.web.core.CorePackage;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.GroupFeedProvider;
import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.RepositoryProvider;
import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.UserFeedProvider;
import org.cloudlet.web.core.WebPlatform;

import java.util.logging.Logger;

@Singleton
public class WebServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(WebServerModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    install(new JpaPersistModule("persist.jpaUnit")); // TODO read from

    requestStaticInjection(InjectionListener.class);

    bind(CorePackage.class).asEagerSingleton();
    bind(WebPlatform.class).asEagerSingleton();
    bind(Repository.class).toProvider(RepositoryProvider.class);
    bind(UserFeed.class).annotatedWith(Root.class).toProvider(UserFeedProvider.class);
    bind(GroupFeed.class).annotatedWith(Root.class).toProvider(GroupFeedProvider.class);
    bind(BookFeed.class).annotatedWith(Root.class).toProvider(BookFeedProvider.class);

    // MethodInterceptor finderInterceptor = new JpaFinderProxy();
    // requestInjection(finderInterceptor);
    // bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }
}