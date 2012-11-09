package org.cloudlet.web.core;

import org.cloudlet.web.core.service.RepositoryService;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Path("/")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(RepositoryService.class)
public class Repository extends Entry {

  @Override
  @XmlTransient
  public RepositoryService getService() {
    return (RepositoryService) super.getService();
  }

}
