package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.server.GroupFeedServiceImpl;

@ImplementedBy(GroupFeedServiceImpl.class)
public interface GroupFeedService extends FeedService<GroupFeed, Group> {

}
