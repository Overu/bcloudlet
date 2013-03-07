package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class MediaService extends Service {

  public Media update(Media media) {
    return (Media) super.update(media);
  }
}
