package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.BookFeed)
@XmlType(name = CorePackage.BookFeed)
@Entity(name = CorePackage.BookFeed)
@Table(name = CorePackage.BookFeed)
@Path(CorePackage.BOOKS)
@DefaultField(key = "title", value = "图书")
public class BookFeed extends PagingFeed<Book> {

  private boolean featured;

  private boolean promoted;

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Book createEntry(Book book) {
    return super.createEntry(book);
  }

  @GET
  @Path(CorePackage.FEATURED)
  public BookFeed findFeaturedBooks() {
    featured = true;
    doLoad();
    return this;
  }

  @GET
  @Path(CorePackage.PROMOTED)
  public BookFeed findPromotedBooks() {
    promoted = true;
    doLoad();
    return this;
  }

  @GET
  @Path(CorePackage.TAGGED)
  public BookFeed findTaggedBooks(@QueryParam("tag") String tag) {
    featured = true;
    doLoad();
    return this;
  }

  @Override
  public Class<Book> getEntryType() {
    return Book.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.BookFeed;
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
    super.doLoadEntries();
  }

  @Override
  protected void initQueryConditions(StringBuilder sql) {
    super.initQueryConditions(sql);
    if (featured) {
      sql.append(" and f.featured=true");
    }
    if (promoted) {
      sql.append(" and f.promoted=true");
    }
  }

  @Override
  protected void initQueryParams(TypedQuery<Book> query) {
    super.initQueryParams(query);
  }

}
