package org.cloudlet.web.boot.client;

import java.util.logging.Logger;

import org.cloudlet.web.boot.shared.BootSharedModule;

import com.google.gwt.inject.client.AbstractGinModule;

final class BootGinModule extends AbstractGinModule {
	private static final Logger logger = Logger.getLogger(BootGinModule.class
			.getName());

	@Override
	protected void configure() {
		logger.finest("config start");
		install(new BootSharedModule());
	}
}
