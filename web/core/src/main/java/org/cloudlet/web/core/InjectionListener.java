package org.cloudlet.web.core;

import javax.persistence.PostLoad;

public class InjectionListener {

	@PostLoad
	void onPostLoad(final Object domain) {
		WebPlatform.get().injectMembers(domain);
	}
}
