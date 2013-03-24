package org.cloudlet.core.server;

import javax.persistence.PostLoad;

public class InjectionListener {

	@PostLoad
	void onPostLoad(final Object domain) {
		WebPlatform.get().injectMembers(domain);
	}
}
