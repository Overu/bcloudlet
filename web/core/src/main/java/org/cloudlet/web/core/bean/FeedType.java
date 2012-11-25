package org.cloudlet.web.core.bean;


public class FeedType<F extends FeedBean<E>, E extends ResourceBean> extends ResourceType<F> {

  private ResourceType<E> entryType;

  public FeedType(ResourceType parent, String name, ResourceType<E> entryType) {
    super(parent, name);
    this.entryType = entryType;
  }

  public ResourceType<E> getEntryType() {
    return entryType;
  }

  public ResourceType getTargetType(String path) {
    return entryType;
  }
}
