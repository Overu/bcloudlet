package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.BookBean;
import org.cloudlet.web.core.bean.BookFeedBean;

@Singleton
public class BookFeedService extends FeedService<BookFeedBean, BookBean> {

  public BookFeedService() {
    super(BookFeedBean.class);
  }
}
