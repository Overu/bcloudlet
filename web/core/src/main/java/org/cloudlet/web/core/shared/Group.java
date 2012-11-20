package org.cloudlet.web.core.shared;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Group.TYPE_NAME)
@XmlType(name = Group.TYPE_NAME)
@Entity
@Table(name = "t_group")
@Handler(GroupService.class)
@Path("group")
@DefaultField(key = "title", value = "用户组")
public class Group extends Resource implements Principal {

  public static final String TYPE_NAME = "Group";

  public static ResourceType TYPE = new ResourceType(Resource.TYPE, TYPE_NAME) {
    @Override
    public Group createInstance() {
      return new Group();
    }
  };

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
  public ResourceType getResourceType() {
    return TYPE;
  }

  public void setName(String name) {
    this.name = name;
  }
}
