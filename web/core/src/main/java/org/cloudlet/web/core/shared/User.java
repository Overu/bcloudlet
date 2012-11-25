package org.cloudlet.web.core.shared;

public interface User extends Resource {

  String getEmail();

  String getName();

  String getPhone();

  String getState();

  String getZip();

  void setEmail(String value);

  void setName(String value);

  void setPhone(String value);

  void setState(String state);

  void setZip(String zip);

}
