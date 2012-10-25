package org.cloudlet.web.core.client;

import com.google.gwt.inject.client.AbstractGinModule;

public class CoreGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(Launcher.class).asEagerSingleton();
	}

}
