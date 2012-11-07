package org.cloudlet.web.core.shared;

public class CorePackage {

  public static class Content {
    public static final String CHILDREN = "children";
    public static PlaceType TYPE = new PlaceType("content");
  }

  public static class Entry {
    public static EntryType TYPE = new EntryType(Content.TYPE, "entry");
  }

  public static class Feed {
    public static FeedType TYPE = new FeedType(Content.TYPE, "feed", Entry.TYPE);
  }

  public static class Group {
    public static EntryType TYPE = new EntryType(Entry.TYPE, "group");
  }

  public static class GroupFeed {
    public static FeedType TYPE = new FeedType(Feed.TYPE, "groupFeed", Group.TYPE);
  }

  public static class Repository {
    public static PlaceType TYPE = new PlaceType(Content.TYPE, "repository");
    static {
      TYPE.addChild("users", UserFeed.TYPE);
      TYPE.addChild("groups", GroupFeed.TYPE);
    }
  }

  public static class User {
    public static EntryType TYPE = new EntryType(Entry.TYPE, "user");
  }

  public static class UserFeed {
    public static FeedType TYPE = new FeedType(Feed.TYPE, "userFeed", User.TYPE);
  }

}
