package org.cloudlet.web.core.service;

import com.google.inject.Singleton;


@Singleton
public class BookFeedProvider extends ResourceProvider<BookFeedBean> {
  public BookFeedProvider() {
    super(BookFeedBean.class);
  }
}
