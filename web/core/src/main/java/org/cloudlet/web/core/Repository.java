package org.cloudlet.web.core;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Repository.TYPE)
@XmlType(name = Repository.TYPE)
@Entity(name = Repository.TYPE)
@Table(name = Repository.TYPE)
@Path("/")
public final class Repository extends Content {

  public static final String TYPE = CorePackage.PREFIX + "Repository";

  @Override
  public String getPath() {
    return "";
  }

  @Override
  public String getResourceType() {
    return TYPE;
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
