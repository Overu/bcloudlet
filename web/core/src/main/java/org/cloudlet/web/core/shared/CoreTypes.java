package org.cloudlet.web.core.shared;

public class CoreTypes {

  public static PlaceType Content = new PlaceType("content");

  public static EntryType Entry = new EntryType(Content, "entry");
  public static FeedType Feed = new FeedType(Content, "feed", Entry);

  public static EntryType Group = new EntryType(Entry, "group");
  public static EntryType User = new EntryType(Entry, "user");

  public static FeedType GroupFeed = new FeedType(Feed, "groupFeed", Group);
  public static FeedType UserFeed = new FeedType(Feed, "userFeed", User);

  static {
    Group.addReference("users", UserFeed);
    Group.addReference("groups", GroupFeed);
  }

}
