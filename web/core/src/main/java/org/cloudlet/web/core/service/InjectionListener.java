package org.cloudlet.web.core.service;

import javax.persistence.PostLoad;

public class InjectionListener {

	@PostLoad
	void onPostLoad(final Object domain) {
		WebPlatform.get().injectMembers(domain);
	}
}
