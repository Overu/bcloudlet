package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.service.UserBean;
import org.cloudlet.web.core.service.UserFeedBean;

import java.util.LinkedHashSet;
import java.util.Set;

public class JpaRealm extends AuthorizingRealm {

  public static final String ALGORITHM_NAME = Sha1Hash.ALGORITHM_NAME;

  private final Provider<UserFeedBean> userService;

  @Inject
  JpaRealm(@Root Provider<UserFeedBean> userService) {
    this.userService = userService;

    HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM_NAME);
    setCredentialsMatcher(matcher);
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String userName = upToken.getUsername();

    if (userName == null) {
      throw new AccountException("Null usernames are not allowed by this realm.");
    }

    UserBean user = userService.get().findUserByName(userName);
    if (user == null) {
      return null;
    }
    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, user.getPasswordHash(), getName());
    return info;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
    // null usernames are invalid
    if (principals == null) {
      throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
    }

    String userName = (String) getAvailablePrincipal(principals);
    UserBean user = userService.get().findUserByName(userName);

    Set<String> roleNames = new LinkedHashSet<String>();
    Set<String> permissions = new LinkedHashSet<String>();

    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
    info.setStringPermissions(permissions);
    return info;
  }

}
