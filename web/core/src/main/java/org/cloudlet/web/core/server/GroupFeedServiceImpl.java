package org.cloudlet.web.core.server;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.service.GroupFeedService;

import com.google.inject.Singleton;

@Singleton
public class GroupFeedServiceImpl extends FeedServiceImpl<GroupFeed, Group>
		implements GroupFeedService {

}
