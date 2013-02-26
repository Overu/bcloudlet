package org.cloudlet.web.core.server;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.persistence.NoResultException;

@Singleton
public class UserService extends FeedService<Users, User> {

  public UserService() {
    super(Users.class, User.class);
  }

  public User findUserByName(final String name) {
    User toRtn = null;
    try {
      toRtn = em().createQuery("select u from CoreUser u where u.name = :name", User.class).setParameter("name", name).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @Transactional
  public void updatePassword(final String userName, final String newPwd) {
    User user = findUserByName(userName);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + userName);
    }
    String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME, newPwd).toHex();
    user.setPasswordHash(hashedPwd);
    em().persist(user);
  }
}
