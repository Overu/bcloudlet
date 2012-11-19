package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.MemberFeedServiceImpl;

@ImplementedBy(MemberFeedServiceImpl.class)
public interface MemberFeedService extends FeedService<MemberFeed, Member> {

}
