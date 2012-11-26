package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.UserFeedBean;

@Singleton
public class UserFeedProvider extends ResourceProvider<UserFeedBean> {

  public UserFeedProvider() {
    super(UserFeedBean.class);
  }

}
