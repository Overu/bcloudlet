package org.cloudlet.web.core;

import org.cloudlet.web.core.service.MemberService;
import org.cloudlet.web.core.service.Service;

import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "t_member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(MemberService.class)
public class Member extends Entry {

  public static Logger logger = Logger.getLogger(Member.class.getName());

  private String roleName;

  private String roleType;

  private Role role;

  @XmlTransient
  public Role getRole() {
    if (role == null && roleType != null && roleName != null) {
      try {
        Class<?> cls = Class.forName(roleType);
        if (Enum.class.isAssignableFrom(cls)) {
          Class<? extends Enum> enumClass = (Class<? extends Enum>) cls;
          role = (Role) Enum.valueOf(enumClass, roleName);
        } else {
          Handler handler = cls.getAnnotation(Handler.class);
          Class<Service> serviceClass = (Class<Service>) handler.value();
          Service service = WebPlatform.getInstance().getService(serviceClass);
          role = (Role) service.getById(roleName, (Class<? extends Content>) cls);
        }
      } catch (ClassNotFoundException e) {
        logger.severe(e.getMessage());
      }
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
