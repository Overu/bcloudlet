package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.SectionFeedServiceImpl;

@ImplementedBy(SectionFeedServiceImpl.class)
public interface SectionFeedService extends FeedService<SectionFeed, Section> {

}
