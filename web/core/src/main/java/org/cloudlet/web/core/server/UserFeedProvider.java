package org.cloudlet.web.core.server;

import com.google.inject.Singleton;


@Singleton
public class UserFeedProvider extends ResourceProvider<UserFeed> {

  public UserFeedProvider() {
    super(UserFeed.class);
  }

}
