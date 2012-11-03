package org.cloudlet.web.core.shared;

public class FeedType extends PlaceType {

  private EntryType entryType;

  public FeedType(PlaceType parent, String name, EntryType entryType) {
    super(parent, name);
    this.entryType = entryType;
  }

  @Override
  public PlaceType getTargetType(String path) {
    PlaceType type = super.getTargetType(path);
    if (type != null) {
      return type;
    }
    return entryType;
  }
}
