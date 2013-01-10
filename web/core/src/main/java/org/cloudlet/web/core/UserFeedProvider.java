package org.cloudlet.web.core;

import com.google.inject.Singleton;


@Singleton
public class UserFeedProvider extends ResourceProvider<UserFeed> {

  public UserFeedProvider() {
    super(UserFeed.class);
  }

}
