package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.BookBean;

@Singleton
public class BookService extends ResourceService<BookBean> {

  public BookService() {
    super(BookBean.class);
  }
}
