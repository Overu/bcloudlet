package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;
import org.cloudlet.web.core.shared.Role;

import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Member)
@XmlType(name = CorePackage.Member)
@Entity(name = CorePackage.Member)
@Table(name = CorePackage.Member)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Member extends Entry {
  public static Logger logger = Logger.getLogger(Member.class.getName());

  private String roleName;

  private String roleType;

  private Role role;

  @Override
  public String getResourceType() {
    return CorePackage.Member;
  }

  @XmlTransient
  public Role getRole() {
    if (role == null && roleType != null && roleName != null) {
      getObject(roleType, roleName);
    }
    return role;
  }

  public String getRoleName() {
    return roleName;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRole(Role role) {
    this.role = role;
    this.roleName = role.getName();
    this.roleType = role.getClass().getName();
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
    this.role = null;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
    this.role = null;
  }

}
