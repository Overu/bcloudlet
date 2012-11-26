package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.persistence.PostLoad;

public class InjectionListener {

	@Inject
	private static Injector injector;

	@PostLoad
	void onPostLoad(final Object domain) {
		injector.injectMembers(domain);
	}
}
