package org.cloudlet.web.core.shared;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@MappedSuperclass
public abstract class PagingFeed<E extends Resource> extends Feed<E> {

  public static FeedType TYPE = new FeedType(Feed.TYPE, "pagingFeed", Resource.TYPE);

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
      entries = getService().findEntries(this, 0, limit);
    }
    queryCount = getService().countEntries(this);
  }

  @Override
  public FeedType<? extends Feed<?>, ? extends E> getResourceType() {
    return TYPE;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
  }
}
