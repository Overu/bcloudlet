package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupFeed;
import org.cloudlet.web.core.shared.GroupFeedService;

import com.google.inject.Singleton;

@Singleton
public class GroupFeedServiceImpl extends FeedServiceImpl<GroupFeed, Group>
		implements GroupFeedService {

}
