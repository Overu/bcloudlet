package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.server.UserFeedServiceImpl;

@ImplementedBy(UserFeedServiceImpl.class)
public interface UserFeedService extends FeedService<UserFeed, User> {

}
