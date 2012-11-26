package org.cloudlet.web.core.bean;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity
@Path("group")
@DefaultField(key = "title", value = "用户组")
public class GroupBean extends ResourceBean implements Principal {

  protected String name;

  @OneToOne
  protected GroupFeedBean groups;

  @OneToOne
  protected MemberFeedBean members;

  @Path("groups")
  @DefaultField(key = "title", value = "用户组")
  @XmlTransient
  public GroupFeedBean getGroups() {
    return groups;
  }

  @Path("members")
  @DefaultField(key = "title", value = "成员")
  @XmlTransient
  public MemberFeedBean getMembers() {
    return members;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
