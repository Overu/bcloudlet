package org.cloudlet.web.core;


import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = User.TYPE)
@XmlType(name = User.TYPE)
@Entity(name = User.TYPE)
@Table(name = User.TYPE)
public class User extends Content implements Principal {
  public static final String TYPE = CorePackage.PREFIX + "User";
  public static final String EMAIL = "email";
  public static final String NAME = "name";
  public static final String PHONE = "phone";
  public static final String STATE = "state";
  public static final String ZIP = "zip";
  private String name;

  private String email;

  private String phone;

  private String state;

  private String passwordHash;

  private String zip;

  public String getEmail() {
    return email;
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * @return the passwordHash
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  public String getPhone() {
    return phone;
  }

  @Override
  public String getResourceType() {
    return TYPE;
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

  /**
   * @param passwordHash the passwordHash to set
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
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

  @PUT
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public User update(User data) {
    readFrom(data);
    save();
    return data;
  }

  protected void readFrom(User delta) {
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
