package org.cloudlet.web.core.servlet;

import com.google.inject.name.Names;

import org.apache.shiro.guice.web.ShiroWebModule;
import org.cloudlet.web.core.server.JpaRealm;

import javax.servlet.ServletContext;

public class WebSecurityModule extends ShiroWebModule {
  public WebSecurityModule(final ServletContext sc) {
    super(sc);
  }

  @Override
  protected void configureShiroWeb() {
    addFilterChain("/public/**", ANON);
    addFilterChain("/stuff/allowed/**", AUTHC_BASIC, config(PERMS, "yes"));
    addFilterChain("/stuff/forbidden/**", AUTHC_BASIC, config(PERMS, "no"));
    // addFilterChain("/**", AUTHC_BASIC);

    bindRealm().to(JpaRealm.class);
    bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);
  }
}
