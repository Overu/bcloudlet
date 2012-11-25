package org.cloudlet.web.core.server;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.jpa.JpaPersistModule;

import org.aopalliance.intercept.MethodInterceptor;
import org.cloudlet.web.core.bean.BookFeedBean;
import org.cloudlet.web.core.bean.GroupFeedBean;
import org.cloudlet.web.core.bean.RepositoryBean;
import org.cloudlet.web.core.bean.UserFeedBean;
import org.cloudlet.web.core.bean.WebPlatform;
import org.cloudlet.web.core.service.BookFeedService;
import org.cloudlet.web.core.service.GroupFeedService;
import org.cloudlet.web.core.service.RepositoryService;
import org.cloudlet.web.core.service.UserFeedService;

import java.util.logging.Logger;

@Singleton
public class WebServerModule extends AbstractModule {

  private final Logger logger = Logger.getLogger(WebServerModule.class.getName());

  @java.lang.Override
  protected void configure() {
    logger.finest("Begin configure " + getClass().getName());

    install(new JpaPersistModule("persist.jpaUnit")); // TODO read from

    bind(WebPlatform.class).asEagerSingleton();
    bind(RepositoryBean.class).toProvider(RepositoryService.class);
    bind(UserFeedBean.class).toProvider(UserFeedService.class);
    bind(GroupFeedBean.class).toProvider(GroupFeedService.class);
    bind(BookFeedBean.class).toProvider(BookFeedService.class);

    MethodInterceptor finderInterceptor = new JpaFinderProxy();
    requestInjection(finderInterceptor);
    bindInterceptor(any(), annotatedWith(Finder.class), finderInterceptor);
    logger.finest("End configure " + getClass().getName());
  }
}