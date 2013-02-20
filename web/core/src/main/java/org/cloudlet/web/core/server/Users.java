package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Users)
@XmlType(name = CorePackage.Users)
@Entity(name = CorePackage.Users)
@Table(name = CorePackage.Users)
@Path("users")
public class Users extends Feed<User> {

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
  public String getResourceType() {
    return CorePackage.Users;
  }

  @Override
  public Class<UserService> getServiceType() {
    return UserService.class;
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
