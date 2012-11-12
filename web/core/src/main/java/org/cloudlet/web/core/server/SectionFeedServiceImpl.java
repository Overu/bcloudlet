package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Section;
import org.cloudlet.web.core.shared.SectionFeed;
import org.cloudlet.web.core.shared.SectionFeedService;

@Singleton
public class SectionFeedServiceImpl extends FeedServiceImpl<SectionFeed, Section> implements
    SectionFeedService {

}
