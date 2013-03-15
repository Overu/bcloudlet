package org.cloudlet.web.core.server;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class BookQuery extends QueryFeed<Book> {

  @Override
  @XmlTransient
  public Class<Book> getEntryType() {
    return Book.class;
  }

  @Override
  public void setParams(TypedQuery query) {
    super.setParams(query);
    Books books = WebPlatform.get().getRepository().getBooks();
    query.setParameter("parent", books);
  }
}
