package org.cloudlet.web.core;

import org.cloudlet.web.core.service.RepositoryService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Path("/")
@Entity
@Handler(RepositoryService.class)
public class Repository extends Content {

  public Repository() {
  }

  @Path("groups")
  @Relation("用户组")
  @XmlTransient
  public GroupFeed getGroups() {
    return (GroupFeed) getChild("groups");
  }

  @Override
  @XmlTransient
  public RepositoryService getService() {
    return (RepositoryService) super.getService();
  }

  @Path("users")
  @Relation("系统用户")
  @XmlTransient
  public UserFeed getUsers() {
    return (UserFeed) getChild("users");
  }

}
