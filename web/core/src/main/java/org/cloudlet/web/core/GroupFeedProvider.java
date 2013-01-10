package org.cloudlet.web.core;

import com.google.inject.Singleton;


@Singleton
public class GroupFeedProvider extends ResourceProvider<GroupFeed> {

  public GroupFeedProvider() {
    super(GroupFeed.class);
  }

}
