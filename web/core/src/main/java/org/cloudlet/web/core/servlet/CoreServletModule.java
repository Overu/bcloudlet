package org.cloudlet.web.core.servlet;

import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.impl.BaseProxyCategory;
import com.google.web.bindery.requestfactory.shared.impl.EntityProxyCategory;
import com.google.web.bindery.requestfactory.shared.impl.ValueProxyCategory;

import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.cloudlet.web.core.server.CoreResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CoreServletModule extends ServletModule {
  @AutoBeanFactory.Category(value = { EntityProxyCategory.class, ValueProxyCategory.class, BaseProxyCategory.class })
  @AutoBeanFactory.NoWrap(EntityProxyId.class)
  interface Factory extends AutoBeanFactory {
  }

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Override
  protected void configureServlets() {
    logger.finest("installPersistModule begin");
    filter("/*").through(PersistFilter.class);

    requestStaticInjection(DatabaseConnectionProvider.class);
    filter("/*").through(DatabaseConnectionFilter.class);

    filter("/*").through(GuiceShiroFilter.class);
    serve("/_ah/login_required").with(OpenIdAuthServlet.class);

    Map<String, String> jaxRsParams = new HashMap<String, String>();
    jaxRsParams.put(ServletProperties.JAXRS_APPLICATION_CLASS, CoreResourceConfig.class.getName());
    serve("/api/*").with(ServletContainer.class, jaxRsParams);
    bind(org.glassfish.jersey.servlet.ServletContainer.class).in(Singleton.class);

    install(new WebSecurityModule(getServletContext()));
    logger.finest("installPersistModule end");
  }

}
