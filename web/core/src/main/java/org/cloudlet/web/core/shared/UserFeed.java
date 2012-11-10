package org.cloudlet.web.core.shared;


import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Handler(UserFeedService.class)
@Path("users")
public class UserFeed extends PagingFeed<User> {

  public static FeedType<UserFeed, User> TYPE = new FeedType<UserFeed, User>(PagingFeed.TYPE,
      "userFeed", User.TYPE) {
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
  public FeedType<UserFeed, User> getObjectType() {
    return TYPE;
  }
}
