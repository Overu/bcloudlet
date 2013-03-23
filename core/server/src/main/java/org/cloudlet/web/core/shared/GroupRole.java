package org.cloudlet.web.core.shared;


import java.util.Set;

public enum GroupRole implements Role {
  MANAGER, CONTRIBUTOR, READER;

  @Override
  public String getDescription() {
    return name();
  }

  @Override
  public Set<Role> getImplicitRoles() {
    return null;
  }

  @Override
  public String getLocalizedName() {
    return name();
  }

  @Override
  public String getName() {
    return name();
  }

  @Override
  public Set<Permission> getPermissions() {
    return null;
  }
}
