package org.cloudlet.web.core;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.service.BookBean;

@ImplementedBy(BookBean.class)
public interface Book extends Resource {
  String TYPE = CorePackage.PREFIX + "Book";

  Media getCover();

  void setCover(Media cover);
}
