package org.cloudlet.web.core;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.cloudlet.web.core.server.JpaRealm;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
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

@XmlRootElement(name = UserFeed.TYPE)
@XmlType(name = UserFeed.TYPE)
@Entity(name = UserFeed.TYPE)
@Table(name = UserFeed.TYPE)
@Path("users")
@DefaultField(key = "title", value = "系统用户")
public class UserFeed extends PagingFeed<User> {
  public static final String TYPE = CorePackage.PREFIX + "UserFeed";

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public User createEntry(User user) {
    return super.createEntry(user);
  }

  public User findUserByName(final String name) {
    User toRtn = null;
    try {
      toRtn =
          em().createQuery("select u from CoreUser u where u.name = :name", User.class).setParameter("name", name).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @Override
  public Class<User> getEntryType() {
    return User.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

  @Override
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml" })
  public UserFeed load() {
    doLoad();
    return this;
  }

  @Override
  public User newEntry() {
    User user = super.newEntry();
    user.setName("abc");
    user.setEmail("abc@mycompany.com");
    return user;
  }

  @PUT
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public UserFeed update(UserFeed feed) {
    readFrom(feed);
    save();
    return this;
  }

  public void updatePassword(final String userName, final String newPwd) {
    User user = findUserByName(userName);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + userName);
    }
    String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME, newPwd).toHex();
    user.setPasswordHash(hashedPwd);
    saveAndCommit(user);
  }
}
