package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = UserFeed.TYPE_NAME)
@XmlType(name = UserFeed.TYPE_NAME)
@Entity
@Handler(UserFeedService.class)
@Path("users")
@DefaultField(key = "title", value = "系统用户")
public class UserFeed extends PagingFeed<User> {
  public static final String TYPE_NAME = "UserFeed";

  public static FeedType<UserFeed, User> TYPE = new FeedType<UserFeed, User>(PagingFeed.TYPE,
      TYPE_NAME, User.TYPE) {
    @Override
    public UserFeed createInstance() {
      return new UserFeed();
    }
  };

  @Override
  public Class<User> getEntryType() {
    return User.class;
  }

  @Override
  public FeedType<UserFeed, User> getResourceType() {
    return TYPE;
  }
}
