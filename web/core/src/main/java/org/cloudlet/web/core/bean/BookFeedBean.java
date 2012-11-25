package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.BookFeedService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity
@Path("books")
@DefaultField(key = "title", value = "图书")
@Handler(BookFeedService.class)
public class BookFeedBean extends PagingFeedBean<BookBean> {

  public static final String MY_BOOKS = "myBooks";

  public static final String STARRED = "starred";

  public static final String RECOMMENDED = "recommended";

  @Override
  public Class<BookBean> getEntryType() {
    return BookBean.class;
  }

  @Override
  public BookBean newEntry() {
    return new BookBean();
  }

  @Override
  protected void doLoadEntries() {
    if (STARRED.equals(renditionKind)) {
    } else if (RECOMMENDED.equals(renditionKind)) {
    } else if (MY_BOOKS.equals(renditionKind)) {
    } else {
      super.doLoadEntries();
    }
  }

}
