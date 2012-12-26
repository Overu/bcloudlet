package org.cloudlet.web.core.servlet;

import com.google.inject.Key;
import com.google.inject.name.Names;

import org.apache.shiro.guice.web.ShiroWebModule;
import org.cloudlet.web.core.server.JpaRealm;
import org.cloudlet.web.core.server.OAuthInitializer;

import javax.servlet.ServletContext;

import io.buji.oauth.OAuthRealm;
import io.buji.oauth.filter.OAuthRolesAuthorizationFilter;

public class WebSecurityModule extends ShiroWebModule {

  @SuppressWarnings({ "UnusedDeclaration" })
  public static final Key<OAuthRolesAuthorizationFilter> GOOGLE_ROLES = Key.get(OAuthRolesAuthorizationFilter.class, Google.class);

  @SuppressWarnings({ "UnusedDeclaration" })
  public static final Key<OAuthRolesAuthorizationFilter> YAHOO_ROLES = Key.get(OAuthRolesAuthorizationFilter.class, Yahoo.class);

  public WebSecurityModule(final ServletContext sc) {
    super(sc);
  }

  @Override
  protected void configureShiroWeb() {
    // addFilterChain("/public/**", ANON);
    // addFilterChain("/stuff/allowed/**", AUTHC_BASIC, config(PERMS, "yes"));
    // addFilterChain("/stuff/forbidden/**", AUTHC_BASIC, config(PERMS, "no"));
    // addFilterChain("/**", AUTHC_BASIC);

    addFilterChain("/google/**", GOOGLE_ROLES);
    addFilterChain("/yahoo/**", YAHOO_ROLES);

    bindRealm().to(JpaRealm.class);
    bindRealm().to(OAuthRealm.class);
    bind(OAuthInitializer.class).asEagerSingleton();
    bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);
  }
}
