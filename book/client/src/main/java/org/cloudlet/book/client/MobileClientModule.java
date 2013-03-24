package org.cloudlet.book.client;

import com.google.gwt.inject.client.AbstractGinModule;

public class MobileClientModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(MobileApp.class).asEagerSingleton();
  }
}
