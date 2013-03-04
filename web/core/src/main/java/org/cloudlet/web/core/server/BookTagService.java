package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class BookTagService extends FeedService<BookTags, BookTag> {

  public BookTagService() {
    super(BookTags.class, BookTag.class);
  }

}
