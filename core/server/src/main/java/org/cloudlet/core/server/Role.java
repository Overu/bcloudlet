package org.cloudlet.core.server;

import java.io.Serializable;
import java.util.Set;

public interface Role extends Serializable {

  String getDescription();

  Set<Role> getImplicitRoles();

  String getLocalizedName();

  String getName();

  Set<Permission> getPermissions();

  int ordinal();
}
