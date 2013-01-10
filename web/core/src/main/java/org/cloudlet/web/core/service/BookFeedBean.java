package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = BookFeedBean.TYPE)
@XmlType(name = BookFeedBean.TYPE)
@Entity(name = BookFeedBean.TYPE)
@Table(name = BookFeedBean.TYPE)
@Path("books")
@DefaultField(key = "title", value = "图书")
public class BookFeedBean extends PagingFeedBean<BookBean> {
  public static final String TYPE = CorePackage.PREFIX + "BookFeed";
  public static final String MY_BOOKS = "myBooks";

  public static final String STARRED = "starred";

  public static final String RECOMMENDED = "recommended";

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public BookBean createEntry(BookBean book) {
    return super.createEntry(book);
  }

  @Override
  public Class<BookBean> getEntryType() {
    return BookBean.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

  @Override
  public BookBean newEntry() {
    BookBean result = new BookBean();
    result.setParent(this);
    return result;
  }

  @Override
  protected BookBean createFrom(MultivaluedMap<String, String> params) {
    return newEntry();
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
