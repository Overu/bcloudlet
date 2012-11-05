package org.cloudlet.web.core;

import org.cloudlet.web.core.service.GroupFeedService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Handler(GroupFeedService.class)
@Path("groups")
public class GroupFeed extends Feed<Group> {
  @Override
  public Class<Group> getEntryType() {
    return Group.class;
  }
}
