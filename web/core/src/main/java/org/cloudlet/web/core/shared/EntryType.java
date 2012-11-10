package org.cloudlet.web.core.shared;

public class EntryType<T extends Entry> extends ObjectType<T> {

  public EntryType(ObjectType parent, String name) {
    super(parent, name);
  }

}
