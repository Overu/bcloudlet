package org.cloudlet.web.core;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.service.BookFeedBean;

@ImplementedBy(BookFeedBean.class)
public interface BookFeed extends PagingFeed<Book> {
  String TYPE = CorePackage.PREFIX + "BookFeed";
}
