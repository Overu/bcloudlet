package org.cloudlet.core.server;

import org.glassfish.jersey.server.mvc.Template;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Repository.TYPE_NAME)
@Template
@Path("/")
@Produces("text/html;qs=5")
public final class Repository extends Item {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Repository";

  public static final String BOOKS = "books";

  public static final String USERS = "users";

  public static final String GROUPS = "groups";

  public static final String TAGS = "tags";

  public static final String ORDERS = "orders";

  @OneToOne
  private Users users;

  @OneToOne
  private Groups groups;

  @OneToOne
  private Books books;

  @OneToOne
  private Orders orders;

  @OneToOne
  private Tags tags;

  @Context
  private transient HttpServletRequest request;

  @Context
  @Transient
  private HttpServletResponse response;

  @Path(BOOKS)
  public Books getBooks() {
    return books;
  }

  @Path(GROUPS)
  public Groups getGroups() {
    return groups;
  }

  @Path(ORDERS)
  public Orders getOrders() {
    return orders;
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

  public final void save() {
    WebPlatform.get().getInstance(ContentService.class).save(this);
  }

  public void setBooks(Books books) {
    this.books = books;
  }

  public void setGroups(Groups groups) {
    this.groups = groups;
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
  protected boolean doInit() {
    super.doInit();
    users = createChild(USERS, Users.class);
    books = createChild(BOOKS, Books.class);
    tags = createChild(TAGS, Tags.class);
    return true;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    tags.load();
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
