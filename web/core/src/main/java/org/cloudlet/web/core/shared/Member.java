package org.cloudlet.web.core.shared;

import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Member.TYPE_NAME)
@XmlType(name = Member.TYPE_NAME)
@Entity
@Table(name = "t_member")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(MemberService.class)
public class Member extends Entry {

  public static final String TYPE_NAME = "Member";

  public static EntryType<Member> TYPE = new EntryType<Member>(Entry.TYPE, "member") {
    @Override
    public Member createInstance() {
      return new Member();
    }
  };

  public static Logger logger = Logger.getLogger(Member.class.getName());

  private String roleName;

  private String roleType;

  private Role role;

  @XmlTransient
  public Role getRole() {
    if (role == null && roleType != null && roleName != null) {
      WebPlatform.getInstance().getObject(roleType, roleName);
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
