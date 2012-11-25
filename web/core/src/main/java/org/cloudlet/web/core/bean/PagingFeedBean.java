package org.cloudlet.web.core.bean;


import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@MappedSuperclass
public abstract class PagingFeedBean<E extends ResourceBean> extends FeedBean<E> {

  public static FeedType TYPE = new FeedType(FeedBean.TYPE, "pagingFeed", ResourceBean.TYPE);

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
  public FeedType<? extends FeedBean<?>, ? extends E> getResourceType() {
    return TYPE;
  }

  @Override
  protected void doLoad() {
    super.doLoad();
  }
}
