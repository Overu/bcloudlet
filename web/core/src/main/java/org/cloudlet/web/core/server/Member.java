package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.Role;

import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Member.TYPE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Member extends Entry {
  public static Logger logger = Logger.getLogger(Member.class.getName());

  private String roleName;

  private String roleType;

  private Role role;

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Member";

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

  @Override
  public String getType() {
    return Member.TYPE_NAME;
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
