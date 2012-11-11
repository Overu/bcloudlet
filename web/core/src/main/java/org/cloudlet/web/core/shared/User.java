package org.cloudlet.web.core.shared;

import java.security.Principal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Table(name = "t_user")
@Handler(UserService.class)
public class User extends Entry implements Principal {

  public static EntryType TYPE = new EntryType(Entry.TYPE, "user") {
    @Override
    public Entry createInstance() {
      return new User();
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
  public EntryType getResourceType() {
    return TYPE;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  @Override
  public void readFrom(Resource delta) {
    super.readFrom(delta);
    readFrom((User) delta);
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
