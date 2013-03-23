package org.cloudlet.web.core.server;

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
    Books books = WebPlatform.get().getRepository().getBooks();
    query.setParameter("parent", books);
    query.setParameter("tag", tag.getId());
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
    Repository repo = (Repository) getRoot();
    repo.getTags().doLoad();
  }
}
