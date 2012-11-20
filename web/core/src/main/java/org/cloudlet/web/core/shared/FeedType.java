package org.cloudlet.web.core.shared;

public class FeedType<F extends Feed<E>, E extends Resource> extends ResourceType<F> {

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
