package org.cloudlet.web.core.shared;

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

  public static EntryType<Repository> TYPE = new EntryType<Repository>(Entry.TYPE, "repository") {
    @Override
    public Repository createInstance() {
      return new Repository();
    }
  };

  @Override
  public EntryType getObjectType() {
    return TYPE;
  }

  @Override
  @XmlTransient
  public RepositoryService getService() {
    return (RepositoryService) super.getService();
  }

}
