package org.cloudlet.web.core;

import org.cloudlet.web.core.service.RepositoryService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Path("/")
@Entity
@Handler(RepositoryService.class)
public class CoreRepository extends Repository {

  @Path("groups")
  @DefaultField(key = "title", value = "用户组")
  @XmlTransient
  public GroupFeed getGroups() {
    return (GroupFeed) getChild("groups");
  }

  @Path("users")
  @DefaultField(key = "title", value = "系统用户")
  @XmlTransient
  public UserFeed getUsers() {
    return (UserFeed) getChild("users");
  }
}
