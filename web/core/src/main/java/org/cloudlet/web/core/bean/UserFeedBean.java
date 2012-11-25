package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.UserFeedService;
import org.cloudlet.web.core.shared.UserFeed;

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

@XmlRootElement(name = UserFeedBean.TYPE_NAME)
@XmlType(name = UserFeedBean.TYPE_NAME)
@Entity
@Handler(UserFeedService.class)
@Path("users")
@DefaultField(key = "title", value = "系统用户")
public class UserFeedBean extends PagingFeedBean<UserBean> {

  public static final String TYPE_NAME = "UserFeed";

  public static FeedType<UserFeedBean, UserBean> TYPE = new FeedType<UserFeedBean, UserBean>(
      PagingFeedBean.TYPE, TYPE_NAME, UserBean.TYPE) {
    @Override
    public UserFeedBean createInstance() {
      return new UserFeedBean();
    }
  };

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserFeedBean create(UserFeedBean feed) {
    for (UserBean u : feed.getEntries()) {
      createEntry(u);
    }
    return this;
  }

  @Override
  public Class<UserBean> getEntryType() {
    return UserBean.class;
  }

  @Override
  public FeedType<UserFeedBean, UserBean> getResourceType() {
    return TYPE;
  }

  @Override
  public Class<UserFeed> getType() {
    return UserFeed.class;
  }

  @Override
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml"})
  public UserFeedBean load() {
    doLoad();
    return this;
  }

  @Override
  public UserBean newEntry() {
    UserBean user = new UserBean();
    user.setName("abc");
    user.setEmail("abc@mycompany.com");
    return user;
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserFeedBean update(UserFeedBean feed) {
    readFrom(feed);
    update();
    return this;
  }
}
