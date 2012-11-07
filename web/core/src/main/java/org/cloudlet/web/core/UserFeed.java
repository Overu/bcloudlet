package org.cloudlet.web.core;

import org.cloudlet.web.core.service.UserFeedService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Handler(UserFeedService.class)
@Path("users")
public class UserFeed extends PagingFeed<User> {

  @Path("creator")
  @Relation("Creator")
  @XmlTransient
  public User getCreator() {
    return (User) getChild("creator");
  }

  @Override
  public Class<User> getEntryType() {
    return User.class;
  }

  @Path("modifier")
  @Relation("Modifier")
  @XmlTransient
  public User getModifier() {
    return (User) getChild("modifier");
  }
}
