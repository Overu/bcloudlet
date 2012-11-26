package org.cloudlet.web.core.service;

import com.google.inject.Singleton;


@Singleton
public class GroupFeedProvider extends ResourceProvider<GroupFeedBean> {

  public GroupFeedProvider() {
    super(GroupFeedBean.class);
  }

}
