package org.cloudlet.web.core;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@MappedSuperclass
public abstract class PagingFeed<E extends Content> extends Feed<E> {

  @QueryParam("start")
  @DefaultValue("0")
  @Transient
  protected int start;

  @QueryParam("limit")
  @DefaultValue("-1")
  @Transient
  protected int limit;

  @Override
  protected void doLoadEntries() {
    if (limit != 0) {
      entries = findEntries(start, limit);
    }
    queryCount = countEntries();
  }
}