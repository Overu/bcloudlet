package org.cloudlet.web.core.service;

import com.google.inject.Singleton;


@Singleton
public class UserFeedProvider extends ResourceProvider<UserFeedBean> {

  public UserFeedProvider() {
    super(UserFeedBean.class);
  }

}
