package org.cloudlet.web.core;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@MappedSuperclass
public abstract class PagingFeed<E extends Entry> extends Feed<E> {

  @QueryParam("start")
  @DefaultValue("0")
  @Transient
  protected int start;

  @QueryParam("limit")
  @DefaultValue("-1")
  @Transient
  protected int limit;

  @Override
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Content load() {
    loadBasicInfo();
    if (limit != 0) {
      entries = getService().findChildren(this, start, limit, getEntryType());
    }
    return this;
  }

}
