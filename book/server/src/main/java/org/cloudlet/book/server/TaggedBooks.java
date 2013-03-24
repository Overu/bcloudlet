package org.cloudlet.book.server;

import org.cloudlet.core.server.QueryFeed;
import org.cloudlet.core.server.Repository;
import org.cloudlet.core.server.Tag;
import org.cloudlet.core.server.WebPlatform;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class TaggedBooks extends QueryFeed<Book> {

  private Tag tag;

  @Override
  public void addJoin(StringBuilder sql) {
    super.addJoin(sql);
    if (tag != null) {
      sql.append(" join e.tags t");
    }
  }

  @Override
  public void doLoad() {
    super.doLoad();
    Repository repo = (Repository) getRoot();
    repo.getTags().doLoad();
  }

  @Override
  @XmlTransient
  public Class<Book> getEntryType() {
    return Book.class;
  }

  public BookRank[] getRanks() {
    return BookRank.values();
  }

  public Tag getTag() {
    return tag;
  }

  @Override
  public void prepareQuery(StringBuilder sql) {
    if (tag != null) {
      sql.append(" and t.id=:tag");
    }
  }

  @Override
  public void setParams(TypedQuery query) {
    super.setParams(query);
    BookStore store = WebPlatform.get().getRepository();
    Books books = store.getBooks();
    query.setParameter("parent", books);
    query.setParameter("tag", tag.getId());
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }
}
