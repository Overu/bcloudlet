package org.cloudlet.web.core.shared;

public class FeedType<F extends Feed<E>, E extends Entry> extends ObjectType<F> {

  private EntryType<E> entryType;

  public FeedType(ObjectType parent, String name, EntryType<E> entryType) {
    super(parent, name);
    this.entryType = entryType;
  }

  public EntryType<E> getEntryType() {
    return entryType;
  }

  public ObjectType getTargetType(String path) {
    return entryType;
  }
}
