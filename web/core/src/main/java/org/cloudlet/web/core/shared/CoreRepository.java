package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "coreRepository")
@Path("")
@Entity
@Handler(RepositoryService.class)
public class CoreRepository extends Repository {

  public static EntryType<CoreRepository> TYPE = new EntryType<CoreRepository>(Repository.TYPE,
      "coreRepository") {
    @Override
    public CoreRepository createInstance() {
      return new CoreRepository();
    };
  };

  @Path("groups")
  @DefaultField(key = "title", value = "用户组")
  @XmlTransient
  public GroupFeed getGroups() {
    return (GroupFeed) getRelationship("groups");
  }

  @Override
  public EntryType<CoreRepository> getResourceType() {
    return TYPE;
  }

  @Path("users")
  @DefaultField(key = "title", value = "系统用户")
  @XmlTransient
  public UserFeed getUsers() {
    return (UserFeed) getRelationship("users");
  }
}
