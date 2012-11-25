package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.UserService;
import org.cloudlet.web.core.shared.User;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = UserBean.TYPE_NAME)
@XmlType(name = UserBean.TYPE_NAME)
@Entity
@Table(name = "t_user")
@Handler(UserService.class)
public class UserBean extends ResourceBean implements Principal {

  public static final String TYPE_NAME = "User";

  public static final String EMAIL = "email";

  public static final String NAME = "name";

  public static final String PHONE = "phone";
  public static final String STATE = "state";
  public static final String ZIP = "zip";

  public static ResourceType<UserBean> TYPE = new ResourceType<UserBean>(ResourceBean.TYPE,
      TYPE_NAME) {
    @Override
    public UserBean createInstance() {
      return new UserBean();
    }
  };

  private String name;

  private String email;

  private String phone;

  private String state;

  private String zip;

  public String getEmail() {
    return email;
  }

  @Override
  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  @Override
  public ResourceType<UserBean> getResourceType() {
    return TYPE;
  }

  public String getState() {
    return state;
  }

  @Override
  public Class<User> getType() {
    return User.class;
  }

  public String getZip() {
    return zip;
  }

  public UserBean setEmail(final String value) {
    this.email = value;
    return this;
  }

  public void setName(final String value) {
    this.name = value;
  }

  public UserBean setPhone(final String value) {
    this.phone = value;
    return this;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserBean update(UserBean data) {
    readFrom(data);
    update();
    return data;
  }

  protected void readFrom(UserBean delta) {
    super.readFrom(delta);
    if (delta.name != null) {
      this.name = delta.name;
    }
    if (delta.email != null) {
      this.email = delta.email;
    }
    if (delta.phone != null) {
      this.phone = delta.phone;
    }
    if (delta.state != null) {
      this.state = delta.state;
    }
    if (delta.zip != null) {
      this.zip = delta.zip;
    }
  }

}
