package org.cloudlet.web.core.server;

import javax.persistence.MappedSuperclass;
import javax.persistence.TypedQuery;

@MappedSuperclass
public abstract class Folder<E extends Item> extends Collection<E> {

  @Override
  public void setParams(TypedQuery query) {
    super.setParams(query);
    query.setParameter("parent", this);
  }

}
