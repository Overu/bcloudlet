package org.cloudlet.web.core.shared;

public class EntryType<T extends Entry> extends ResourceType<T> {

  public EntryType(ResourceType parent, String name) {
    super(parent, name);
  }

}
