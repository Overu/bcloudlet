package org.cloudlet.web.core;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = GroupFeed.TYPE)
@XmlType(name = GroupFeed.TYPE)
@Entity(name = GroupFeed.TYPE)
@Table(name = GroupFeed.TYPE)
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeed extends PagingFeed<Group> {
  public static final String TYPE = CorePackage.PREFIX + "GroupFeed";

  @Override
  public Class<Group> getEntryType() {
    return Group.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

}
