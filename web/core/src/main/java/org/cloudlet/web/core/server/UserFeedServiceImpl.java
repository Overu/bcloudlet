package org.cloudlet.web.core.server;

import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.service.UserFeedService;

import com.google.inject.Singleton;

@Singleton
public class UserFeedServiceImpl extends FeedServiceImpl<UserFeed, User>
		implements UserFeedService {

}
