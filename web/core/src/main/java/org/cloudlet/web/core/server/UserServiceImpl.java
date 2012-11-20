package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserService;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

@Singleton
public class UserServiceImpl extends ResourceServiceImpl<User> implements UserService {

  private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

  @Override
  public User findUserByUsername(final String userName) {
    User toRtn = null;
    try {
      toRtn =
          em().createQuery(
              "select u from com.goodow.web.security.server.domain.User u where u.userName = :userName",
              User.class).setParameter("userName", userName).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @Override
  @RequiresAuthentication
  public void updatePassword(final String userName, final String newPwd) {
    User user = findUserByUsername(userName);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + userName);
    }
    String hashedPwd =
        new SimpleHash(JpaRealm.ALGORITHM_NAME, newPwd.toCharArray(), ByteSource.Util.bytes(user
            .getPhone())).toHex();
    user.setEmail(hashedPwd);
    em().persist(user);
  }

}
