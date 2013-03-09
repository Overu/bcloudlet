package org.cloudlet.web.core.server;

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

@XmlRootElement
@XmlType
@Entity(name = Users.TYPE_NAME)
@Path(Repository.USERS)
public class Users extends Feed<User> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Users";

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public User createEntry(User user) {
    return super.createEntry(user);
  }

  @Override
  public Class<User> getEntryType() {
    return User.class;
  }

  @Override
  public Class<UserService> getServiceType() {
    return UserService.class;
  }

  @Override
  public String getType() {
    return Users.TYPE_NAME;
  }

  @Override
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml" })
  public Users load() {
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
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Users update(Users feed) {
    readFrom(feed);
    update();
    return this;
  }

}
