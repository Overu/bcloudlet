package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Groups.TYPE_NAME)
@Path(Repository.GROUPS)
public class Groups extends Folder<Group> {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Groups";

  @Override
  public Class<Group> getEntryType() {
    return Group.class;
  }

  @Override
  public String getType() {
    return Groups.TYPE_NAME;
  }

}
