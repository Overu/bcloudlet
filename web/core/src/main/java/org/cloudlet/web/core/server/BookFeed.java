package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

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

@XmlRootElement(name = CorePackage.BOOK_FEED)
@XmlType(name = CorePackage.BOOK_FEED)
@Entity(name = CorePackage.BOOK_FEED)
@Table(name = CorePackage.BOOK_FEED)
@Path("books")
@DefaultField(key = "title", value = "图书")
public class BookFeed extends PagingFeed<Book> {

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Book createEntry(Book book) {
    return super.createEntry(book);
  }

  @Override
  public Class<Book> getEntryType() {
    return Book.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.BOOK_FEED;
  }

  @Override
  public Book newEntry() {
    Book result = new Book();
    result.setParent(this);
    return result;
  }

  @Override
  protected Book createFrom(MultivaluedMap<String, String> params) {
    return newEntry();
  }

  @Override
  protected void doLoadEntries() {
    if (CorePackage.STARRED.equals(renditionKind)) {
    } else if (CorePackage.RECOMMENDED.equals(renditionKind)) {
    } else if (CorePackage.MY_BOOKS.equals(renditionKind)) {
    } else {
      super.doLoadEntries();
    }
  }
}
