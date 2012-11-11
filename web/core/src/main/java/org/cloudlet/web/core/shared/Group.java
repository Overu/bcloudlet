package org.cloudlet.web.core.shared;


import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Table(name = "t_group")
@Handler(GroupService.class)
public class Group extends Entry implements Principal {

  public static EntryType TYPE = new EntryType(Entry.TYPE, "group") {
    @Override
    public Entry createInstance() {
      return new Group();
    }
  };

  protected String name;

  @Path("members")
  public MemberFeed getMembers() {
    return (MemberFeed) getRelationship("members");
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
