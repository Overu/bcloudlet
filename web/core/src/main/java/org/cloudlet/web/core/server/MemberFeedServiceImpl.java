package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.Member;
import org.cloudlet.web.core.MemberFeed;
import org.cloudlet.web.core.service.MemberFeedService;

@Singleton
public class MemberFeedServiceImpl extends FeedServiceImpl<MemberFeed, Member> implements
    MemberFeedService {

}
