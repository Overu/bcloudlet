package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.UserFeedServiceImpl;

@ImplementedBy(UserFeedServiceImpl.class)
public interface UserFeedService extends FeedService<UserFeed, User> {

}
