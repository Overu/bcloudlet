package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = BookFeed.TYPE_NAME)
@XmlType(name = BookFeed.TYPE_NAME)
@Entity
@Handler(GroupFeedService.class)
@Path("books")
@DefaultField(key = "title", value = "图书")
public class BookFeed extends PagingFeed<Book> {

  @SuppressWarnings("hiding")
  public static final String TYPE_NAME = "BookFeed";

  public static final String MY_BOOKS = "myBooks";

  public static final String STARRED = "starred";

  public static final String RECOMMENDED = "recommended";

  @SuppressWarnings("hiding")
  public static FeedType<BookFeed, Book> TYPE = new FeedType<BookFeed, Book>(BookFeed.TYPE,
      TYPE_NAME, Book.TYPE) {

    @Override
    public BookFeed createInstance() {
      return new BookFeed();
    }
  };

  @Override
  public Class<Book> getEntryType() {
    return Book.class;
  }

  @Override
  public FeedType<BookFeed, Book> getResourceType() {
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
