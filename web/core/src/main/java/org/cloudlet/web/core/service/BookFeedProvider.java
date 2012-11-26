package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.BookFeedBean;

@Singleton
public class BookFeedProvider extends ResourceProvider<BookFeedBean> {
  public BookFeedProvider() {
    super(BookFeedBean.class);
  }
}
