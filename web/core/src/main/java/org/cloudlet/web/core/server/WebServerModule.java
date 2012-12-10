package org.cloudlet.web.core.server;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.aopalliance.intercept.MethodInterceptor;
import org.cloudlet.web.core.CorePackage;
import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.service.BookFeedBean;
import org.cloudlet.web.core.service.BookFeedProvider;
import org.cloudlet.web.core.service.GroupFeedBean;
import org.cloudlet.web.core.service.GroupFeedProvider;
import org.cloudlet.web.core.service.RepositoryBean;
import org.cloudlet.web.core.service.RepositoryProvider;
import org.cloudlet.web.core.service.UserFeedBean;
import org.cloudlet.web.core.service.UserFeedProvider;
import org.cloudlet.web.core.service.WebPlatform;

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
    bind(RepositoryBean.class).toProvider(RepositoryProvider.class);
    bind(UserFeedBean.class).annotatedWith(Root.class).toProvider(UserFeedProvider.class);
    bind(GroupFeedBean.class).annotatedWith(Root.class).toProvider(GroupFeedProvider.class);
    bind(BookFeedBean.class).annotatedWith(Root.class).toProvider(BookFeedProvider.class);

//    MethodInterceptor finderInterceptor = new JpaFinderProxy();
//    requestInjection(finderInterceptor);
//    bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }
}