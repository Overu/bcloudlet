package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Repository.TYPE_NAME)
@Path("/")
public final class Repository extends Entry {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Repository";

  public static final String BOOKS = "books";

  public static final String USERS = "users";

  public static final String GROUPS = "groups";

  public static final String TAGS = "tags";

  public static final String ORDERS = "orders";

  @Context
  @Transient
  protected ResourceContext resourceContext;

  @Context
  @Transient
  protected UriInfo uriInfo;

  @OneToOne
  private Users users;

  @OneToOne
  private Books books;

  @OneToOne
  private Orders orders;

  @OneToOne
  private Tags tags;

  @Path(BOOKS)
  public Books getBooks() {
    return books;
  }

  @Path(ORDERS)
  public Orders getOrders() {
    return orders;
  }

  @Override
  public Class<RepositoryService> getServiceType() {
    return RepositoryService.class;
  }

  @Path(TAGS)
  public Tags getTags() {
    return tags;
  }

  @Override
  public String getTitle() {
    return "Repository";
  }

  @Override
  @XmlTransient
  public UriBuilder getUriBuilder() {
    if (uriInfo == null) {
      RuntimeDelegate rd = RuntimeDelegate.getInstance();
      return rd.createUriBuilder();
    }
    return uriInfo.getBaseUriBuilder();
  }

  @Path(USERS)
  public Users getUsers() {
    return users;
  }

  public void setBooks(Books books) {
    this.books = books;
  }

  public void setOrders(Orders orders) {
    this.orders = orders;
  }

  public void setTags(Tags tags) {
    this.tags = tags;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  @Override
  protected void initResource(Object result) {
    if (result != null) {
      if (resourceContext != null) {
        resourceContext.initResource(result);
      }
    }
  }
}
