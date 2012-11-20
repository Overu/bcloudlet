package org.cloudlet.web.core.shared;

import com.google.inject.Singleton;

@Singleton
public class CorePackage extends Package {

  public static final String NAME = "org.cloudlet.web.core.shared";

  public static final String PREFIX = "core";

  public CorePackage() {
    setName(NAME);
    addResourceType(Resource.TYPE, Feed.TYPE, Media.TYPE, Repository.TYPE);
    addResourceType(User.TYPE, Group.TYPE, Member.TYPE, UserFeed.TYPE, GroupFeed.TYPE,
        MemberFeed.TYPE);
    addResourceType(Book.TYPE, BookFeed.TYPE, Section.TYPE);
    init();
  }

  @Override
  protected void init() {
    super.init();
    // Operation op1 = new Operation();
    // op1.setHttpMethod("GET");
    // op1.setPath(Feed.ENTRIES);
    // op1.setType(Resource.TYPE);
    // Feed.TYPE.addOperation(op1);
    //
    // Operation op2 = new Operation();
    // op2.setHttpMethod("GET");
    // op2.setPath(Feed.NEW);
    // op2.setType(Resource.TYPE);
    // Feed.TYPE.addOperation(op2);
  }

}
