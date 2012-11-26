package org.cloudlet.web.core;

public class CorePackage {

  public static final String PREFIX = "Core";

  static {
    init();
  }

  public static void init() {
    Registry.register(Repository.TYPE, Repository.class);
    Registry.register(User.TYPE, User.class);
    Registry.register(UserFeed.TYPE, UserFeed.class);
    Registry.register(Group.TYPE, Group.class);
    Registry.register(GroupFeed.TYPE, GroupFeed.class);
    Registry.register(Book.TYPE, Book.class);
    Registry.register(Section.TYPE, Section.class);
    Registry.register(BookFeed.TYPE, BookFeed.class);
    Registry.register(Media.TYPE, Media.class);
    Registry.register(Member.TYPE, Member.class);
    Registry.register(MemberFeed.TYPE, MemberFeed.class);
  }

}
