package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Repository)
@XmlType(name = CorePackage.Repository)
@Entity(name = CorePackage.Repository)
@Table(name = CorePackage.Repository)
@Path("/")
public final class Repository extends Resource {

  @Override
  public String getPath() {
    return null;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Repository;
  }

  @Override
  public String getTitle() {
    return "Repository";
  }

  @Override
  public boolean hasChildren() {
    return true;
  }

}
