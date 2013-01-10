package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = GroupBean.TYPE)
@XmlType(name = GroupBean.TYPE)
@Entity(name = GroupBean.TYPE)
@Table(name = GroupBean.TYPE)
public class GroupBean extends ResourceBean implements Principal {
  public static final String TYPE = CorePackage.PREFIX + "Group";
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

  @Override
  public String getResourceType() {
    return TYPE;
  }

  public void setName(String name) {
    this.name = name;
  }
}
