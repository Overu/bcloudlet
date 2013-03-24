package org.cloudlet.core.servlet;

import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;

import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.cloudlet.core.server.CoreResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CoreServletModule extends ServletModule {

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Override
  protected void configureServlets() {
    logger.finest("installPersistModule begin");
    filter("/*").through(ProxyFilter.class);
    filter("/*").through(PersistFilter.class);

    requestStaticInjection(DatabaseConnectionProvider.class);
    filter("/*").through(DatabaseConnectionFilter.class);

    filter("/*").through(GuiceShiroFilter.class);
    serve("/import").with(ImporterServlet.class);

    Map<String, String> jaxRsParams = new HashMap<String, String>();
    jaxRsParams.put(ServletProperties.JAXRS_APPLICATION_CLASS, CoreResourceConfig.class.getName());
    jaxRsParams.put(ServletProperties.FILTER_FORWARD_ON_404, Boolean.TRUE.toString());
    jaxRsParams.put(ServletProperties.JSP_TEMPLATES_BASE_PATH, "/WEB-INF/jsp/");
    // /store/ios/test.html
    jaxRsParams.put(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(static|store)/.*");

    filter("/*").through(ServletContainer.class, jaxRsParams);

    bind(org.glassfish.jersey.servlet.ServletContainer.class).in(Singleton.class);

    install(new WebSecurityModule(getServletContext()));
    logger.finest("installPersistModule end");
  }

}
