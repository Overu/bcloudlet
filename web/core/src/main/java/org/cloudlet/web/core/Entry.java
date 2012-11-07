package org.cloudlet.web.core;

import org.cloudlet.web.core.service.EntryService;

import javax.persistence.MappedSuperclass;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@MappedSuperclass
@XmlRootElement
public abstract class Entry extends Content {

  @Override
  @POST
  @Consumes(MediaType.WILDCARD)
  @Produces(MediaType.WILDCARD)
  public Content create(Content child) {
    child.setParent(this);
    EntryService<Entry> service = (EntryService<Entry>) getService();
    return service.create(this, child);
  }

}
