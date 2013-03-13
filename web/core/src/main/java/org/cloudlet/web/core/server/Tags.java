package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
  public Tag doCreate(Tag tag) {
    return super.doCreate(tag);
  }

  @Override
  public Class<Tag> getEntryType() {
    return Tag.class;
  }

  public Tag getOrCreateTag(String value, String targetType) {
    try {
      TypedQuery<Tag> query =
          em().createQuery(" from " + Tag.TYPE_NAME + " t where t.targetType=:targetType and t.value=:value", Tag.class);
      query.setParameter("targetType", targetType);
      query.setParameter("value", value);
      return query.getSingleResult();
    } catch (NoResultException e) {
      Tag tag = new Tag();
      tag.setValue(value);
      tag.setTargetType(targetType);
      doCreate(tag);
      return tag;
    }
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
