package org.cloudlet.web.core;

import org.cloudlet.web.core.service.GroupService;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "t_group")
@Handler(GroupService.class)
public class Group extends Entry implements Principal {

  protected String name;

  @Path("members")
  public MemberFeed getMembers() {
    return (MemberFeed) getChild("members");
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
