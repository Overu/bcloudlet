package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.Member;
import org.cloudlet.web.core.MemberFeed;
import org.cloudlet.web.core.server.MemberFeedServiceImpl;

@ImplementedBy(MemberFeedServiceImpl.class)
public interface MemberFeedService extends FeedService<MemberFeed, Member> {

}
