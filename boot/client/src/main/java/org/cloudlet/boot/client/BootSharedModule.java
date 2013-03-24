package org.cloudlet.boot.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import java.util.logging.Logger;

public final class BootSharedModule extends AbstractGinModule {
	private static final Logger logger = Logger
			.getLogger(BootSharedModule.class.getName());

	@Override
	protected void configure() {
		logger.finest("config start");
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
	}
}
