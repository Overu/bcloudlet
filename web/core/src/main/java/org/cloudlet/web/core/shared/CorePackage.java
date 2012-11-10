package org.cloudlet.web.core.shared;

import com.google.inject.Singleton;

@Singleton
public class CorePackage extends Package {

  public CorePackage() {
    addContentTypes(Content.TYPE, Entry.TYPE, User.TYPE, Group.TYPE, Member.TYPE, Repository.TYPE,
        CoreRepository.TYPE, UserFeed.TYPE, GroupFeed.TYPE);
  }

}
