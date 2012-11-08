package org.cloudlet.web.core;

import org.cloudlet.web.core.service.UserService;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Table(name = "t_user")
@Handler(UserService.class)
public class User extends Entry implements Principal {

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

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public User setEmail(final String value) {
    this.email = value;
    return this;
  }

  public void setName(final String value) {
    this.name = value;
  }

  public User setPhone(final String value) {
    this.phone = value;
    return this;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @Override
  protected void readFrom(Content delta) {
    super.readFrom(delta);
    readFrom((User) delta);
  }

  protected void readFrom(User delta) {
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
