package org.cloudlet.web.core.shared;


import com.google.inject.name.Names;

import org.apache.shiro.guice.ShiroModule;
import org.cloudlet.web.core.server.JpaRealm;

public class ShiroTestModule extends ShiroModule {

  @Override
  protected void configureShiro() {
    bindRealm().to(JpaRealm.class);
    bindConstant().annotatedWith(Names.named("shiro.globalSessionTimeout")).to(30000L);

    // bind(CredentialsMatcher.class).to(HashedCredentialsMatcher.class);
    // bind(HashedCredentialsMatcher.class);
    // bindConstant().annotatedWith(Names.named("shiro.hashAlgorithmName"))
    // .to(Sha1Hash.ALGORITHM_NAME);
  }

}
