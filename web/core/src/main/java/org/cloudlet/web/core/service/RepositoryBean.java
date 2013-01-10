package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = RepositoryBean.TYPE)
@XmlType(name = RepositoryBean.TYPE)
@Entity(name = RepositoryBean.TYPE)
@Table(name = RepositoryBean.TYPE)
@Path("/")
public final class RepositoryBean extends ResourceBean {

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
