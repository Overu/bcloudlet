package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.UserServiceImpl;

@ImplementedBy(UserServiceImpl.class)
public interface UserService extends ResourceService<User> {

  User findUserByUsername(final String userName);

  void updatePassword(final String userName, final String newPwd);
}
