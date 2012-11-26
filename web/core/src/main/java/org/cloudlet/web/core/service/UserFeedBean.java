package org.cloudlet.web.core.service;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cloudlet.web.core.UserFeed;
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
public class UserFeedBean extends PagingFeedBean<UserBean> {

  @Override
  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserBean createEntry(UserBean user) {
    return super.createEntry(user);
  }

  public UserBean findUserByUsername(final String userName) {
    UserBean toRtn = null;
    try {
      toRtn =
          em().createQuery("select u from com.goodow.web.security.server.domain.User u where u.userName = :userName", UserBean.class)
              .setParameter("userName", userName).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @Override
  public Class<UserBean> getEntryType() {
    return UserBean.class;
  }

  @Override
  public String getType() {
    return UserFeed.TYPE;
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
    UserBean user = super.newEntry();
    user.setName("abc");
    user.setEmail("abc@mycompany.com");
    return user;
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserFeedBean update(UserFeedBean feed) {
    readFrom(feed);
    save();
    return this;
  }

  public void updatePassword(final String userName, final String newPwd) {
    UserBean user = findUserByUsername(userName);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + userName);
    }
    String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME, newPwd.toCharArray(), ByteSource.Util.bytes(user.getPhone())).toHex();
    user.setEmail(hashedPwd);
    em().persist(user);
  }
}
