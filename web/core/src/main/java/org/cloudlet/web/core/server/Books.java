package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Books)
@XmlType(name = CorePackage.Books)
@Entity(name = CorePackage.Books)
@Table(name = CorePackage.Books)
@Path(CorePackage.BOOKS)
public class Books extends Feed<Book> {

  @QueryParam("tag")
  protected boolean featured;

  @QueryParam("tag")
  protected boolean promoted;

  @QueryParam("tag")
  protected String[] tag;

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Book createEntry(Book book) {
    return super.createEntry(book);
  }

  @GET
  @Path(CorePackage.EDIT)
  public Books editBook() {
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
    return CorePackage.Books;
  }

  @Override
  @XmlTransient
  public Class<BookService> getServiceType() {
    return BookService.class;
  }

  public boolean isFeatured() {
    return featured;
  }

  public boolean isPromoted() {
    return promoted;
  }

  @Override
  public Book newEntry() {
    Book result = new Book();
    result.setParent(this);
    return result;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public void setPromoted(boolean promoted) {
    this.promoted = promoted;
  }

  @Override
  protected Book createFrom(MultivaluedMap<String, String> params) {
    return newEntry();
  }

}
