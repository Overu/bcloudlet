package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Launcher {
	@Inject
	public Launcher(UserGrid example) {
		RootPanel.get().add(example);
	}
}