package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.GROUP_FEED)
@XmlType(name = CorePackage.GROUP_FEED)
@Entity(name = CorePackage.GROUP_FEED)
@Table(name = CorePackage.GROUP_FEED)
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeed extends PagingFeed<Group> {
  @Override
  public Class<Group> getEntryType() {
    return Group.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.GROUP_FEED;
  }

}
