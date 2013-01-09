package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Repository;

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
public final class RepositoryBean extends ResourceBean {

  @Override
  public String getPath() {
    return "";
  }

  @Override
  public String getResourceType() {
    return Repository.TYPE;
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
