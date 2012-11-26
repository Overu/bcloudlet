package org.cloudlet.web.core.service;

import org.cloudlet.web.core.BookFeed;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = BookFeed.TYPE)
@XmlType(name = BookFeed.TYPE)
@Entity(name = BookFeed.TYPE)
@Table(name = BookFeed.TYPE)
@Path("books")
@DefaultField(key = "title", value = "图书")
public class BookFeedBean extends PagingFeedBean<BookBean> {

  public static final String MY_BOOKS = "myBooks";

  public static final String STARRED = "starred";

  public static final String RECOMMENDED = "recommended";

  @Override
  public Class<BookBean> getEntryType() {
    return BookBean.class;
  }

  @Override
  public String getType() {
    return BookFeed.TYPE;
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
