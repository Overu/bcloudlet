package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.service.RepositoryService;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(RepositoryService.class)
@Path("/")
public final class RepositoryBean extends ResourceBean {

  @Override
  public String getPath() {
    return "";
  }

  @Override
  @XmlTransient
  public RepositoryService getService() {
    return (RepositoryService) super.getService();
  }

  @Override
  public String getTitle() {
    return "Repository";
  }

  @Override
  public Class<Repository> getType() {
    return Repository.class;
  }

  @Override
  public boolean hasChildren() {
    return true;
  }

}
