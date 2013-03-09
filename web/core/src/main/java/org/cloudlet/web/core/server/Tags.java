package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Tags.TYPE_NAME)
@Path(Repository.TAGS)
public class Tags extends Feed<Tag> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Tags";

  @Override
  @POST
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Tag createEntry(Tag tag) {
    return super.createEntry(tag);
  }

  @Override
  public Class<Tag> getEntryType() {
    return Tag.class;
  }

  @Override
  public Class<TagService> getServiceType() {
    return TagService.class;
  }

  @Override
  public String getType() {
    return TYPE_NAME;
  }

  @PUT
  @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public Tags update(Tags tags) {
    readFrom(tags);
    update();
    return this;
  }

}
