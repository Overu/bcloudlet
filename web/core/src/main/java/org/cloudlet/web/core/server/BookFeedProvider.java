package org.cloudlet.web.core.server;

import com.google.inject.Singleton;


@Singleton
public class BookFeedProvider extends ResourceProvider<BookFeed> {
  public BookFeedProvider() {
    super(BookFeed.class);
  }
}
