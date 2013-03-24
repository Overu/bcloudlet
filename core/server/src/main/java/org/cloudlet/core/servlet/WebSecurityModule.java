package org.cloudlet.core.servlet;

import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import org.apache.shiro.guice.web.ShiroWebModule;
import org.cloudlet.core.server.JpaRealm;
import org.cloudlet.core.server.OAuthInitializer;

import javax.servlet.ServletContext;

import io.buji.oauth.OAuthFilter;
import io.buji.oauth.OAuthRealm;
import io.buji.oauth.filter.OAuthRolesAuthorizationFilter;

public class WebSecurityModule extends ShiroWebModule {

  public static final Key<OAuthFilter> OAUTH = Key.get(OAuthFilter.class);

  public static final Key<OAuthRolesAuthorizationFilter> GOOGLE_ROLES = Key.get(OAuthRolesAuthorizationFilter.class, Google.class);

  public static final Key<OAuthRolesAuthorizationFilter> YAHOO_ROLES = Key.get(OAuthRolesAuthorizationFilter.class, Yahoo.class);

  public WebSecurityModule(final ServletContext sc) {
    super(sc);
  }

  @Override
  public void configure() {
    super.configure();
    bind(OAuthRealm.class).in(Singleton.class);
  }

  @Override
  protected void configureShiroWeb() {
    // addFilterChain("/public/**", ANON);
    // addFilterChain("/stuff/allowed/**", AUTHC_BASIC, config(PERMS, "yes"));
    // addFilterChain("/stuff/forbidden/**", AUTHC_BASIC, config(PERMS, "no"));
    // addFilterChain("/**", AUTHC_BASIC);

    addFilterChain("/google/**", config(GOOGLE_ROLES, "ROLE_USER"));
    addFilterChain("/yahoo/**", config(YAHOO_ROLES, "ROLE_USER"));
    addFilterChain("/shiro-oauth", OAUTH);
    addFilterChain("/logout", LOGOUT);
    addFilterChain("/**", ANON);

    bindRealm().to(JpaRealm.class);
    bindRealm().to(OAuthRealm.class).in(Singleton.class);
    bind(OAuthInitializer.class).asEagerSingleton();
    bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);
  }
}
