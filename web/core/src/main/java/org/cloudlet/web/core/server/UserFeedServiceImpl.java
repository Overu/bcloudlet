package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.UserFeedService;

import com.google.inject.Singleton;

@Singleton
public class UserFeedServiceImpl extends FeedServiceImpl<UserFeed, User>
		implements UserFeedService {

}
