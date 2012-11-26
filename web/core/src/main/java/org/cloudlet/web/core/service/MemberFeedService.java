package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.MemberBean;
import org.cloudlet.web.core.bean.MemberFeedBean;

@Singleton
public class MemberFeedService extends FeedService<MemberFeedBean, MemberBean> {

  public MemberFeedService() {
    super(MemberFeedBean.class);
  }

}
