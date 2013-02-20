package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Repository)
@XmlType(name = CorePackage.Repository)
@Entity(name = CorePackage.Repository)
@Table(name = CorePackage.Repository)
@Path("/")
public final class Repository extends Entry {

  @OneToOne
  private Users users;

  @OneToOne
  private Books books;

  public Books getBooks() {
    return books;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Repository;
  }

  @Override
  public Class<RepositoryService> getServiceType() {
    return RepositoryService.class;
  }

  @Override
  public String getTitle() {
    return "Repository";
  }

  public Users getUsers() {
    return users;
  }

  @Override
  public boolean hasChildren() {
    return true;
  }

  public void setBooks(Books books) {
    this.books = books;
  }

  public void setUsers(Users users) {
    this.users = users;
  }
}
