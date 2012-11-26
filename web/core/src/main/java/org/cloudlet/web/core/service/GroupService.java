package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.GroupBean;

@Singleton
public class GroupService extends ResourceService<GroupBean> {

  public GroupService() {
    super(GroupBean.class);
  }
}
