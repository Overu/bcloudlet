package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.MemberBean;

@Singleton
public class MemberService extends ResourceService<MemberBean> {

  public MemberService() {
    super(MemberBean.class);
  }

}
