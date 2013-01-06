package org.cloudlet.web.core;

public class CorePackage {

  public static final String PREFIX = "Core";

  public static final String REPOSITORY = PREFIX + "Repository";

  public static final String USER = PREFIX + "User";

  public static final String GROUP = PREFIX + "Group";

  public static final String BOOK = PREFIX + "Book";

  public static final String SECTION = PREFIX + "Section";

  public static final String MEDIA = PREFIX + "Media";

  public static final String USERFEED = PREFIX + "UserFeed";

  public static final String GROUPFEED = PREFIX + "GroupFeed";

  public static final String BOOKFEED = PREFIX + "BookFeed";

  public static final String MEMBERFEED = PREFIX + "MemberFeed";

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
