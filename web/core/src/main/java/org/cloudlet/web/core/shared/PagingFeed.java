package org.cloudlet.web.core.shared;


import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@MappedSuperclass
public abstract class PagingFeed<E extends Entry> extends Feed<E> {

  public static FeedType TYPE = new FeedType(Feed.TYPE, "pagingFeed", Entry.TYPE);

  @QueryParam("start")
  @DefaultValue("0")
  @Transient
  protected int start;

  @QueryParam("limit")
  @DefaultValue("-1")
  @Transient
  protected int limit;

  @Override
  public FeedType getResourceType() {
    return TYPE;
  }

  @Override
  protected void loadEntries() {
    if (limit != 0) {
      FeedService service = getService();
      entries = service.findEntries(this, 0, -1);
      queryCount = service.countEntries(this);
    }
  }
}
