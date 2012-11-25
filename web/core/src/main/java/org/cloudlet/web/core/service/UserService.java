package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cloudlet.web.core.bean.UserBean;
import org.cloudlet.web.core.server.JpaRealm;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

@Singleton
public class UserService extends ResourceService<UserBean> {

  private static final Logger logger = Logger.getLogger(UserService.class.getName());

  public UserService() {
    super(UserBean.class);
  }

  public UserBean findUserByUsername(final String userName) {
    UserBean toRtn = null;
    try {
      toRtn =
          em().createQuery(
              "select u from com.goodow.web.security.server.domain.User u where u.userName = :userName",
              UserBean.class).setParameter("userName", userName).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @RequiresAuthentication
  public void updatePassword(final String userName, final String newPwd) {
    UserBean user = findUserByUsername(userName);
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
