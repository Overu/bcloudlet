package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class GroupService extends FeedService<Groups, Group> {

  public GroupService() {
    super(Groups.class, Group.class);
  }

}
