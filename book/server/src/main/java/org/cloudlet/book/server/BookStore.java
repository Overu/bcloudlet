package org.cloudlet.book.server;

import org.cloudlet.core.server.CoreUtil;
import org.cloudlet.core.server.Repository;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = BookStore.TYPE_NAME)
@Path("/")
public final class BookStore extends Repository {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "BookStore";

  public static final String BOOKS = "books";

  public static final String ORDERS = "orders";

  @OneToOne
  private Books books;

  @OneToOne
  private Orders orders;

  @Override
  public void doLoad() {
    super.doLoad();
    books.doLoad();
  }

  @Path(BOOKS)
  public Books getBooks() {
    return books;
  }

  @Path(ORDERS)
  public Orders getOrders() {
    return orders;
  }

  public void setBooks(Books books) {
    this.books = books;
  }

  public void setOrders(Orders orders) {
    this.orders = orders;
  }

  @Override
  protected boolean doInit() {
    super.doInit();
    books = createChild(BOOKS, Books.class);
    return true;
  }

}
