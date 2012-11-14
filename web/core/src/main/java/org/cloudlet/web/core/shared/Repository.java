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
public final class Repository extends Entry {

  public static final String TYPE_NAME = "Repository";

  public static EntryType<Repository> TYPE = new EntryType<Repository>(Entry.TYPE, TYPE_NAME) {
    @Override
    public Repository createInstance() {
      return new Repository();
    }
  };

  @Override
  public EntryType<?> getResourceType() {
    return TYPE;
  }

  @Override
  @XmlTransient
  public RepositoryService getService() {
    return (RepositoryService) super.getService();
  }

}
