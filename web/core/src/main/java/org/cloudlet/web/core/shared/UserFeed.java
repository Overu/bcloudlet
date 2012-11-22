package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserFeed create(UserFeed feed) {
    for (User u : feed.getEntries()) {
      createEntry(u);
    }
    return this;
  }

  @Override
  public Class<User> getEntryType() {
    return User.class;
  }

  @Override
  public FeedType<UserFeed, User> getResourceType() {
    return TYPE;
  }

  @Override
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml"})
  public UserFeed load() {
    doLoad();
    return this;
  }

  @Override
  public User newEntry() {
    User user = new User();
    user.setName("abc");
    user.setEmail("abc@mycompany.com");
    return user;
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserFeed update(UserFeed feed) {
    readFrom(feed);
    update();
    return this;
  }
}
