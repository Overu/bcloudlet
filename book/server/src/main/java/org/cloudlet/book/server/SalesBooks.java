package org.cloudlet.book.server;

import org.cloudlet.core.server.QueryFeed;
import org.cloudlet.core.server.WebPlatform;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class SalesBooks extends QueryFeed<Book> {

  private BookRank rank;

  /*
   * @Override public void doLoad() { switch (rank) { case HOT: start = 0; break; case MONTHLY: start = 10; break; case FREE: start = 20;
   * break; case RATED: start = 5; break; case LATEST: start = 10; break; default: break; }
   * 
   * super.doLoad(); }
   */

  @Override
  @XmlTransient
  public Class<Book> getEntryType() {
    return Book.class;
  }

  public BookRank getRank() {
    return rank;
  }

  public List<BookRank> getRanks() {
    return BookRank.getRanksByClass(this.getClass());
  }

  @Override
  public void setParams(TypedQuery query) {
    super.setParams(query);
    BookStore store = WebPlatform.get().getRepository();
    Books books = store.getBooks();
    query.setParameter("parent", books);
  }

  public void setRank(BookRank rank) {
    this.rank = rank;
  }
}
