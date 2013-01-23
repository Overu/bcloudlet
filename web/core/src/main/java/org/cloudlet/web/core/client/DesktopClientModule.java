package org.cloudlet.web.core.client;

import com.google.gwt.inject.client.AbstractGinModule;

public class DesktopClientModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(DesktopApp.class).asEagerSingleton();
  }
}
