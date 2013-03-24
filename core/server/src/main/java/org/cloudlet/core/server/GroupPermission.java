package org.cloudlet.core.server;


public enum GroupPermission implements org.cloudlet.core.server.Permission {

  READ,

  // add child group
  ADD_CHILD,

  // 修改、删除空间
  ADMIN;
}
