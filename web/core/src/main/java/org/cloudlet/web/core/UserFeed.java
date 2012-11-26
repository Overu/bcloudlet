package org.cloudlet.web.core;

public interface UserFeed extends PagingFeed<User> {
  String TYPE = CorePackage.PREFIX + "UserFeed";
}
