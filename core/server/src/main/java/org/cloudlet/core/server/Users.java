package org.cloudlet.core.server;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
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
@Produces("text/html;qs=5")
public class Users extends Folder<User> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Users";

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public User doCreate(User user) {
    return super.doCreate(user);
  }

  public User findUserByName(final String name) {
    User toRtn = null;
    try {
      toRtn = em().createQuery("select u from CoreUser u where u.name = :name", User.class).setParameter("name", name).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @Override
  public Class<User> getEntryType() {
    return User.class;
  }

  @Override
  public String getType() {
    return Users.TYPE_NAME;
  }

  @Override
  public User newContent() {
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

  public void updatePassword(final String userName, final String newPwd) {
    User user = findUserByName(userName);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + userName);
    }
    String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME, newPwd).toHex();
    user.setPasswordHash(hashedPwd);
    em().persist(user);
  }

}
