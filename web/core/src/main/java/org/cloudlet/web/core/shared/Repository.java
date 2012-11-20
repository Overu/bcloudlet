package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = Repository.TYPE_NAME)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(RepositoryService.class)
@Path("/")
public final class Repository extends Resource {

  public static final String TYPE_NAME = "Repository";

  public static ResourceType<Repository> TYPE = new ResourceType<Repository>(Resource.TYPE,
      TYPE_NAME) {
    @Override
    public Repository createInstance() {
      return new Repository();
    }
  };

  @Override
  public String getPath() {
    return "";
  }

  @Override
  public ResourceType<Repository> getResourceType() {
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
  public boolean hasChildren() {
    return true;
  }

}
