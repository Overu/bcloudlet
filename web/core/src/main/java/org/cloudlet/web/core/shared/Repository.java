package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = Repository.TYPE_NAME)
@Path("/")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Handler(RepositoryService.class)
public final class Repository extends Entry {

  public static final String TYPE_NAME = "Repository";

  public static EntryType<Repository> TYPE = new EntryType<Repository>(Entry.TYPE, TYPE_NAME) {
    @Override
    public Repository createInstance() {
      return new Repository();
    }
  };

  @Override
  public Content getParent() {
    return null;
  }

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
