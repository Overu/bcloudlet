package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Repository)
@XmlType(name = CorePackage.Repository)
@Entity(name = CorePackage.Repository)
@Table(name = CorePackage.Repository)
@Path("/")
public final class Repository extends Entry {
  @Context
  @Transient
  protected UriInfo uriInfo;

  @OneToOne
  private Users users;

  @OneToOne
  private Books books;

  @OneToOne
  private BookTags tags;

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

  public BookTags getTags() {
    return tags;
  }

  @Override
  public String getTitle() {
    return "Repository";
  }

  @Override
  @XmlTransient
  public UriBuilder getUriBuilder() {
    return uriInfo.getBaseUriBuilder();
  }

  public Users getUsers() {
    return users;
  }

  public void setBooks(Books books) {
    this.books = books;
  }

  public void setTags(BookTags tags) {
    this.tags = tags;
  }

  public void setUsers(Users users) {
    this.users = users;
  }
}
