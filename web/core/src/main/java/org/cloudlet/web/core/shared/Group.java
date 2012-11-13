package org.cloudlet.web.core.shared;

import java.security.Principal;

import javax.persistence.Entity;
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
public class Group extends Entry implements Principal {

  public static final String TYPE_NAME = "Group";

  public static EntryType TYPE = new EntryType(Entry.TYPE, TYPE_NAME) {
    @Override
    public Entry createInstance() {
      return new Group();
    }
  };

  protected String name;

  @Path("groups")
  @DefaultField(key = "title", value = "用户组")
  @XmlTransient
  public GroupFeed getGroups() {
    return (GroupFeed) getChild("groups");
  }

  @Path("members")
  public MemberFeed getMembers() {
    return (MemberFeed) getChild("members");
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public EntryType getResourceType() {
    return TYPE;
  }

  public void setName(String name) {
    this.name = name;
  }
}
