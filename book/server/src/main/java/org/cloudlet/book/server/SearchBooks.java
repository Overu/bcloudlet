package org.cloudlet.book.server;

import org.cloudlet.core.server.QueryFeed;
import org.cloudlet.core.server.WebPlatform;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class SearchBooks extends QueryFeed<Book> {

  public String search_value;

  @Override
  public void doLoad() {
    if (search_value != null && !"".equals(search_value)) {
      this.search.add("title|" + search_value);
      this.search.add("summary|" + search_value);
    }
    super.doLoad();
  }

  @Override
  @XmlTransient
  public Class<Book> getEntryType() {
    return Book.class;
  }

  public String getSearch_value() {
    return search_value;
  }

  @Override
  public void setParams(TypedQuery query) {
    super.setParams(query);
    BookStore store = WebPlatform.get().getRepository();
    Books books = store.getBooks();
    query.setParameter("parent", books);
  }

  public void setSearch_value(String search_value) {
    this.search_value = search_value;
  }
}
