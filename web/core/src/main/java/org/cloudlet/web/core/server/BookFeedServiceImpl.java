package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Book;
import org.cloudlet.web.core.shared.BookFeed;
import org.cloudlet.web.core.shared.BookFeedService;

@Singleton
public class BookFeedServiceImpl extends FeedServiceImpl<BookFeed, Book> implements BookFeedService {
}
