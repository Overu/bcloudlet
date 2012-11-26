package org.cloudlet.web.core;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.Messages;

public interface GroupRoleNames extends Messages {

	public static final GroupRoleNames instance = GWT
			.create(GroupRoleNames.class);

	String contributor();

	String guest();

	String manager();

	String moderator();

}
