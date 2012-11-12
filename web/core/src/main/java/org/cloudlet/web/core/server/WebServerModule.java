package org.cloudlet.web.core.server;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.aopalliance.intercept.MethodInterceptor;
import org.cloudlet.web.core.shared.BookFeed;
import org.cloudlet.web.core.shared.CorePackage;
import org.cloudlet.web.core.shared.GroupFeed;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebPlatform;

import java.util.logging.Logger;

@Singleton
public class WebServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(WebServerModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    install(new JpaPersistModule("persist.jpaUnit")); // TODO read from

    bind(CorePackage.class).asEagerSingleton();
    bind(WebPlatform.class).to(WebServerPlatform.class).asEagerSingleton();
    bind(Repository.class).toProvider(RepositoryServiceImpl.class);
    bind(UserFeed.class).toProvider(UserFeedServiceImpl.class);
    bind(GroupFeed.class).toProvider(GroupFeedServiceImpl.class);
    bind(BookFeed.class).toProvider(BookFeedServiceImpl.class);

    MethodInterceptor finderInterceptor = new JpaFinderProxy();
    requestInjection(finderInterceptor);
    bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }
}