package org.cloudlet.core.server;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class SalesBooks extends QueryFeed<Book> {

  private BookRank rank;

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
    Books books = WebPlatform.get().getRepository().getBooks();
    query.setParameter("parent", books);
  }

  public void setRank(BookRank rank) {
    this.rank = rank;
  }

  @Override
  protected void doLoad() {
    switch (rank) {
      case HOT:
        start = 0;
        break;
      case MONTHLY:
        start = 10;
        break;
      case FREE:
        start = 20;
        break;
      case RATED:
        start = 5;
        break;
      case LATEST:
        start = 10;
        break;
      default:
        break;
    }

    super.doLoad();
  }
}
