package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.junit.Before;
import org.junit.Test;

public class JpaRealmTest extends CoreTest {

  String userName = "admin";

  String pwd = "1234";

  @Root
  @Inject
  private UserFeed userService;

  @Inject
  SecurityManager securityManager;

  @Before
  public void initShiro() {
    SecurityUtils.setSecurityManager(securityManager);
  }

  @Test
  public void testLogin() {
    User user = userService.findUserByName(userName);
    if (user == null) {
      user = userService.newEntry();
      user.setName(userName);
      userService.createEntry(user);
    }
    userService.updatePassword(userName, pwd);
    UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
    Subject subject = SecurityUtils.getSubject();
    try {
      subject.login(token);
      userService.updatePassword(userName, pwd);
      // } catch (UnknownAccountException uae) {
      // } catch (IncorrectCredentialsException ice) {
      // } catch (LockedAccountException lae) {
      // } catch (ExcessiveAttemptsException eae) {
      // // } ... catch your own ...
      // } catch (AuthenticationException ae) {
      // // unexpected error?
    } finally {
      subject.logout();
    }
    // No problems, continue on as expected...
  }

  @Test
  public void testPermissonViolate() {
    try {
      userService.updatePassword(userName, pwd);
    } catch (UnauthenticatedException e) {
      return;
    }
  }

}
