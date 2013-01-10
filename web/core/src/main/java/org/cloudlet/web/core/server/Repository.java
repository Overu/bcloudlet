package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.REPOSITORY)
@XmlType(name = CorePackage.REPOSITORY)
@Entity(name = CorePackage.REPOSITORY)
@Table(name = CorePackage.REPOSITORY)
@Path("/")
public final class Repository extends Resource {

  @Override
  public String getPath() {
    return "";
  }

  @Override
  public String getResourceType() {
    return CorePackage.REPOSITORY;
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
