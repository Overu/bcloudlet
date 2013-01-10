package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Group)
@XmlType(name = CorePackage.Group)
@Entity(name = CorePackage.Group)
@Table(name = CorePackage.Group)
public class Group extends Resource implements Principal {

  protected String name;

  @OneToOne
  protected GroupFeed groups;

  @OneToOne
  protected MemberFeed members;

  @Path("groups")
  @DefaultField(key = "title", value = "用户组")
  @XmlTransient
  public GroupFeed getGroups() {
    return groups;
  }

  @Path("members")
  @DefaultField(key = "title", value = "成员")
  @XmlTransient
  public MemberFeed getMembers() {
    return members;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Group;
  }

  public void setName(String name) {
    this.name = name;
  }
}
