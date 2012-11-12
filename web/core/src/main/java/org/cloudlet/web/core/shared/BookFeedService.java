package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.BookFeedServiceImpl;

@ImplementedBy(BookFeedServiceImpl.class)
public interface BookFeedService extends FeedService<BookFeed, Book> {

}
