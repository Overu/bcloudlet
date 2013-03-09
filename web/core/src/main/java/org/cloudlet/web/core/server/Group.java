package org.cloudlet.web.core.server;


import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Group.TYPE_NAME)
public class Group extends Entry implements Principal {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Group";

  protected String name;

  @OneToOne
  protected Groups groups;

  @OneToOne
  protected Members members;

  @Path("groups")
  @XmlTransient
  public Groups getGroups() {
    return groups;
  }

  @Path("members")
  @XmlTransient
  public Members getMembers() {
    return members;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getType() {
    return Group.TYPE_NAME;
  }

  @Override
  public Class<GroupService> getServiceType() {
    return GroupService.class;
  }

  public void setName(String name) {
    this.name = name;
  }
}
