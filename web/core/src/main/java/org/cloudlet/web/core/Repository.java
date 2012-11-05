package org.cloudlet.web.core;

import com.google.inject.Singleton;

import org.cloudlet.web.core.service.RepositoryService;

import javax.persistence.Entity;
import javax.ws.rs.Path;

@Singleton
@Path("/")
@Entity
@Handler(RepositoryService.class)
public class Repository extends Content {

  public Repository() {
  }

  @Path("groups")
  public GroupFeed getGroups() {
    return (GroupFeed) getChild("groups");
  }

  @Override
  public RepositoryService getService() {
    return (RepositoryService) super.getService();
  }

  @Path("users")
  public UserFeed getUsers() {
    return (UserFeed) getChild("users");
  }

  @Override
  public Content load() {
    return super.load();
  }

  @Override
  protected <T extends Content> T create(T child) {
    child = getService().create(this, child);
    getChildren().put(child.getPath(), child);
    return child;
  }

}
