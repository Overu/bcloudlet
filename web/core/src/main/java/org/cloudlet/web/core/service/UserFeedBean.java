package org.cloudlet.web.core.service;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.cloudlet.web.core.CorePackage;
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

@XmlRootElement(name = UserFeedBean.TYPE)
@XmlType(name = UserFeedBean.TYPE)
@Entity(name = UserFeedBean.TYPE)
@Table(name = UserFeedBean.TYPE)
@Path("users")
@DefaultField(key = "title", value = "系统用户")
public class UserFeedBean extends PagingFeedBean<UserBean> {
  public static final String TYPE = CorePackage.PREFIX + "UserFeed";

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public UserBean createEntry(UserBean user) {
    return super.createEntry(user);
  }

  public UserBean findUserByName(final String name) {
    UserBean toRtn = null;
    try {
      toRtn =
          em().createQuery("select u from CoreUser u where u.name = :name", UserBean.class).setParameter("name", name).getSingleResult();
    } catch (NoResultException e) {
    }
    return toRtn;
  }

  @Override
  public Class<UserBean> getEntryType() {
    return UserBean.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

  @Override
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "application/ios+xml" })
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
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public UserFeedBean update(UserFeedBean feed) {
    readFrom(feed);
    save();
    return this;
  }

  public void updatePassword(final String userName, final String newPwd) {
    UserBean user = findUserByName(userName);
    if (user == null) {
      throw new UnknownAccountException("找不到用户名: " + userName);
    }
    String hashedPwd = new SimpleHash(JpaRealm.ALGORITHM_NAME, newPwd).toHex();
    user.setPasswordHash(hashedPwd);
    saveAndCommit(user);
  }
}
