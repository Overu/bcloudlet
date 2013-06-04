package org.cloudlet.core.servlet;

import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import org.apache.shiro.guice.web.ShiroWebModule;
import org.cloudlet.core.server.JpaRealm;
import org.cloudlet.core.server.OAuthInitializer;

import javax.servlet.ServletContext;

import io.buji.pac4j.ClientFilter;
import io.buji.pac4j.ClientRealm;
import io.buji.pac4j.filter.ClientRolesAuthorizationFilter;

public class WebSecurityModule extends ShiroWebModule {

  public static final Key<ClientFilter> OAUTH = Key.get(ClientFilter.class);

  public static final Key<ClientRolesAuthorizationFilter> GOOGLE_ROLES = Key.get(ClientRolesAuthorizationFilter.class, Google.class);

  public static final Key<ClientRolesAuthorizationFilter> YAHOO_ROLES = Key.get(ClientRolesAuthorizationFilter.class, Yahoo.class);

  public WebSecurityModule(final ServletContext sc) {
    super(sc);
  }

  @Override
  public void configure() {
    super.configure();
    bind(ClientRealm.class).in(Singleton.class);
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
    bindRealm().to(ClientRealm.class).in(Singleton.class);
    bind(OAuthInitializer.class).asEagerSingleton();
    bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);
  }
}
