package org.cloudlet.web.core;

import org.cloudlet.web.core.service.UserFeedService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Handler(UserFeedService.class)
@Path("users")
public class UserFeed extends PagingFeed<User> {
  @Override
  public Class<User> getEntryType() {
    return User.class;
  }
}
