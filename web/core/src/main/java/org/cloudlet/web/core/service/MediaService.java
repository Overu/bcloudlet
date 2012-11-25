package org.cloudlet.web.core.service;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.MediaBean;

@Singleton
public class MediaService extends ResourceService<MediaBean> {

  public MediaService() {
    super(MediaBean.class);
  }
}
