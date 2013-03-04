package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.BookTags)
@XmlType(name = CorePackage.BookTags)
@Entity(name = CorePackage.BookTags)
@Table(name = CorePackage.BookTags)
@Path("tags")
public class BookTags extends Feed<BookTag> {

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public BookTag createEntry(BookTag tag) {
    return super.createEntry(tag);
  }

  @Override
  public Class<BookTag> getEntryType() {
    return BookTag.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.BookTags;
  }

  @Override
  public Class<BookTagService> getServiceType() {
    return BookTagService.class;
  }

  @PUT
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public BookTags update(BookTags tags) {
    readFrom(tags);
    update();
    return this;
  }

}
