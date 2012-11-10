package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.GroupFeedServiceImpl;

@ImplementedBy(GroupFeedServiceImpl.class)
public interface GroupFeedService extends FeedService<GroupFeed, Group> {

}
