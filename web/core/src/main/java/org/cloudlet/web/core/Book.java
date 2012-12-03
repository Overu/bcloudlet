package org.cloudlet.web.core;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.service.BookBean;

@ImplementedBy(BookBean.class)
public interface Book extends Resource {

  String SECTIONS = "sections";

  String TYPE = CorePackage.PREFIX + "Book";

  String COVER = "cover";

  String SOURCE = "source";

  Media getCover();

  Media getSource();

  void setCover(Media cover);

  void setSource(Media source);
}
