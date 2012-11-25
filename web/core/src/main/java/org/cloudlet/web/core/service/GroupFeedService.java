package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.GroupBean;
import org.cloudlet.web.core.bean.GroupFeedBean;

@Singleton
public class GroupFeedService extends FeedService<GroupFeedBean, GroupBean> {

  public GroupFeedService() {
    super(GroupFeedBean.class);
  }

}
