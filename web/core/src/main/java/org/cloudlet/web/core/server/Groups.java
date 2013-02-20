package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Groups)
@XmlType(name = CorePackage.Groups)
@Entity(name = CorePackage.Groups)
@Table(name = CorePackage.Groups)
@Path("groups")
public class Groups extends Feed<Group> {

  @Override
  public Class<Group> getEntryType() {
    return Group.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Groups;
  }

  @Override
  public Class<GroupService> getServiceType() {
    return GroupService.class;
  }

}
