package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.GroupService;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = GroupBean.TYPE_NAME)
@XmlType(name = GroupBean.TYPE_NAME)
@Entity
@Table(name = "t_group")
@Handler(GroupService.class)
@Path("group")
@DefaultField(key = "title", value = "用户组")
public class GroupBean extends ResourceBean implements Principal {

  public static final String TYPE_NAME = "Group";

  public static ResourceType TYPE = new ResourceType(ResourceBean.TYPE, TYPE_NAME) {
    @Override
    public GroupBean createInstance() {
      return new GroupBean();
    }
  };

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
  public ResourceType getResourceType() {
    return TYPE;
  }

  public void setName(String name) {
    this.name = name;
  }
}
