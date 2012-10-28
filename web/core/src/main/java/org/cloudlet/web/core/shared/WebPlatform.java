package org.cloudlet.web.core.shared;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class WebPlatform {

	@Inject
	private static WebPlatform instance;

	@Inject
	private Injector injector;

	public Injector getInjector() {
		return injector;
	}

	public static WebPlatform getInstance() {
		return instance;
	}

}
