package org.cloudlet.web.core.shared;

public class FeedType<F extends Feed<E>, E extends Entry> extends ResourceType<F> {

  private EntryType<E> entryType;

  public FeedType(ResourceType parent, String name, EntryType<E> entryType) {
    super(parent, name);
    this.entryType = entryType;
  }

  public EntryType<E> getEntryType() {
    return entryType;
  }

  public ResourceType getTargetType(String path) {
    return entryType;
  }
}
