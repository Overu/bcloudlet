package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Member;
import org.cloudlet.web.core.shared.MemberFeed;
import org.cloudlet.web.core.shared.MemberFeedService;

@Singleton
public class MemberFeedServiceImpl extends FeedServiceImpl<MemberFeed, Member> implements
    MemberFeedService {

}
