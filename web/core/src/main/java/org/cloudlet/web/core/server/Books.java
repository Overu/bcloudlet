package org.cloudlet.web.core.server;

import javax.persistence.Entity;
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

@XmlRootElement
@XmlType
@Entity(name = Books.TYPE_NAME)
@Path(Repository.BOOKS)
@Produces("text/html;qs=5")
public class Books extends Feed<Book> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Books";

  @QueryParam("recommended")
  protected boolean featured;

  @QueryParam("promoted")
  protected boolean promoted;

  @QueryParam(Book.TAG)
  protected String tag;

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Book doCreate(Book book) {
    return super.doCreate(book);
  }

  @GET
  @Path(Content.EDIT)
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
  public String getType() {
    return TYPE_NAME;
  }

  public boolean isFeatured() {
    return featured;
  }

  public boolean isPromoted() {
    return promoted;
  }

  @Override
  public void joinSQL(StringBuilder sql) {
    super.joinSQL(sql);
    if (tag != null) {
      sql.append(" join e.tags t");
    }
  }

  @Override
  public Book newEntry() {
    Book result = new Book();
    result.setParent(this);
    return result;
  }

  @Override
  public void prepareQuery(StringBuilder sql) {
    if (featured) {
      sql.append(" and e.featured=true");
    }
    if (promoted) {
      sql.append(" and e.promoted=true");
    }
    if (tag != null) {
      sql.append(" and t.id=:tag");
    }
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  @Override
  public void setParams(TypedQuery query) {
    super.setParams(query);
    if (tag != null) {
      query.setParameter("tag", tag);
    }
  }

  public void setPromoted(boolean promoted) {
    this.promoted = promoted;
  }

  @Override
  protected Book createFrom(MultivaluedMap<String, String> params) {
    return newEntry();
  }

}
