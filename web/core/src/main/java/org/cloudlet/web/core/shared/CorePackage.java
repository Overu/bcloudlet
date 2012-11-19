package org.cloudlet.web.core.shared;

import com.google.inject.Singleton;

@Singleton
public class CorePackage extends Package {

  public static final String NAME = "org.cloudlet.web.core.shared";

  public static final String PREFIX = "core";

  public CorePackage() {
    setName(NAME);
    addResourceType(Resource.TYPE, Entry.TYPE, Rendition.TYPE, Repository.TYPE);
    addResourceType(User.TYPE, Group.TYPE, Member.TYPE, UserFeed.TYPE, GroupFeed.TYPE,
        MemberFeed.TYPE);
    addResourceType(Book.TYPE, BookFeed.TYPE, Section.TYPE);
    init();
  }

}
