package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.BookFeedService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = BookFeedBean.TYPE_NAME)
@XmlType(name = BookFeedBean.TYPE_NAME)
@Entity
@Path("books")
@DefaultField(key = "title", value = "图书")
@Handler(BookFeedService.class)
public class BookFeedBean extends PagingFeedBean<BookBean> {

  @SuppressWarnings("hiding")
  public static final String TYPE_NAME = "BookFeed";

  public static final String MY_BOOKS = "myBooks";

  public static final String STARRED = "starred";

  public static final String RECOMMENDED = "recommended";

  @SuppressWarnings("hiding")
  public static FeedType<BookFeedBean, BookBean> TYPE = new FeedType<BookFeedBean, BookBean>(
      PagingFeedBean.TYPE, TYPE_NAME, BookBean.TYPE) {

    @Override
    public BookFeedBean createInstance() {
      return new BookFeedBean();
    }
  };

  @Override
  public Class<BookBean> getEntryType() {
    return BookBean.class;
  }

  @Override
  public FeedType<BookFeedBean, BookBean> getResourceType() {
    return TYPE;
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
