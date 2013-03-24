package org.cloudlet.book.client;

import com.google.gwt.inject.client.AbstractGinModule;

public class DesktopClientModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(DesktopApp.class).asEagerSingleton();
  }
}
