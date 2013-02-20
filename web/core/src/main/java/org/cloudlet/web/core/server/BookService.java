package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class BookService extends FeedService<Books, Book> {

  public BookService() {
    super(Books.class, Book.class);
  }

  @Override
  protected void initQueryConditions(Books feed, StringBuilder sql) {
    super.initQueryConditions(feed, sql);
    if (feed.isFeatured()) {
      sql.append(" and f.featured=true");
    }
    if (feed.isPromoted()) {
      sql.append(" and f.promoted=true");
    }
  }
}
