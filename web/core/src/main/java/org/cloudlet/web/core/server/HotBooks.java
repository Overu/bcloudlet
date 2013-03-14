package org.cloudlet.web.core.server;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlTransient;

public class HotBooks extends Feed<Book> {

  @Override
  public List<Book> findItems() {
    Class<Book> entryClass = getEntryType();
    StringBuilder sql = new StringBuilder("select e from ");
    joinSQL(sql);
    sql.append(" where e.parent=:parent");
    prepareQuery(sql);
    buildSearch(sql);
    buildSort(sql);
    TypedQuery<Book> query = em().createQuery(sql.toString(), entryClass);
    if (getStart() != null) {
      query.setFirstResult(getStart());
    }
    if (getLimit() != null && getLimit() > 0) {
      query.setMaxResults(getLimit());
    }
    Books books = WebPlatform.get().getRepository().getBooks();
    query.setParameter("parent", books);
    setParams(query);
    return query.getResultList();
  }

  @Override
  @XmlTransient
  public Class<Book> getEntryType() {
    return Book.class;
  }
}
