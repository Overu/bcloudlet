package com.goodow.web.security.server.auth;

import static org.junit.Assert.fail;

import com.google.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.cloudlet.web.core.service.BaseRepositoryTest;
import org.cloudlet.web.core.shared.UserService;
import org.junit.Before;
import org.junit.Test;

public class JpaRealmTest extends BaseRepositoryTest {

  String userName = "admin";
  String pwd = "1234";

  @Inject
  private UserService userService;

  @Inject
  SecurityManager securityManager;

  @Before
  public void initShiro() {
    SecurityUtils.setSecurityManager(securityManager);
  }

  @Test
  public void testLogin() {
    UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
    Subject currentUser = SecurityUtils.getSubject();
    try {
      currentUser.login(token);
      userService.updatePassword(userName, pwd);
      // } catch (UnknownAccountException uae) {
      // } catch (IncorrectCredentialsException ice) {
      // } catch (LockedAccountException lae) {
      // } catch (ExcessiveAttemptsException eae) {
      // // } ... catch your own ...
      // } catch (AuthenticationException ae) {
      // // unexpected error?
    } finally {
      currentUser.logout();
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
    fail("should not reach here");
  }

}
