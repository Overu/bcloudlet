package org.cloudlet.web.core.shared;

import com.google.inject.Singleton;

@Singleton
public class CorePackage extends Package {

  public static final String NAME = "org.cloudlet.web.core.shared";

  public CorePackage() {
    setName(NAME);
    addResourceType(Resource.TYPE, Entry.TYPE, Content.TYPE, Repository.TYPE);
    addResourceType(User.TYPE, Group.TYPE, Member.TYPE, UserFeed.TYPE, GroupFeed.TYPE,
        MemberFeed.TYPE);
    init();
  }

}
