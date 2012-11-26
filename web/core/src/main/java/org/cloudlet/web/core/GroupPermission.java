package org.cloudlet.web.core;


public enum GroupPermission implements org.cloudlet.web.core.Permission {

  READ,

  // add child group
  ADD_CHILD,

  // 修改、删除空间
  ADMIN;
}
