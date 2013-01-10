package org.cloudlet.web.core.server;

import com.google.inject.Singleton;


@Singleton
public class GroupFeedProvider extends ResourceProvider<GroupFeed> {

  public GroupFeedProvider() {
    super(GroupFeed.class);
  }

}
