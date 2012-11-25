package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.SectionBean;

@Singleton
public class SectionService extends ResourceService<SectionBean> {

  public SectionService() {
    super(SectionBean.class);
  }
}
