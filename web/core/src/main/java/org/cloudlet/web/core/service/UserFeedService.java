package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.UserBean;
import org.cloudlet.web.core.bean.UserFeedBean;

@Singleton
public class UserFeedService extends FeedService<UserFeedBean, UserBean> {

  public UserFeedService() {
    super(UserFeedBean.class);
  }

}
