package org.cloudlet.web.core.service;

import org.cloudlet.web.core.User;

public interface UserService extends EntryService<User> {

	User findUserByUsername(final String userName);

	void updatePassword(final String userName, final String newPwd);
}
