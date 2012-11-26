package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.GroupFeedBean;

@Singleton
public class GroupFeedProvider extends ResourceProvider<GroupFeedBean> {

  public GroupFeedProvider() {
    super(GroupFeedBean.class);
  }

}
