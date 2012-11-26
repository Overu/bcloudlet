package org.cloudlet.web.core;

public interface User extends Resource {

  String TYPE = CorePackage.PREFIX + "User";

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
