package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.RepositoryService;
import org.cloudlet.web.core.shared.Repository;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = RepositoryBean.TYPE_NAME)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(RepositoryService.class)
@Path("/")
public final class RepositoryBean extends ResourceBean {

  public static final String TYPE_NAME = "Repository";

  public static ResourceType<RepositoryBean> TYPE = new ResourceType<RepositoryBean>(
      ResourceBean.TYPE, TYPE_NAME) {
    @Override
    public RepositoryBean createInstance() {
      return new RepositoryBean();
    }
  };

  @Override
  public String getPath() {
    return "";
  }

  @Override
  public ResourceType<RepositoryBean> getResourceType() {
    return TYPE;
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
