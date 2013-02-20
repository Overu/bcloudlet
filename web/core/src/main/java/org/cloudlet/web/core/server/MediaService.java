package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class MediaService extends EntryService<Media> {

  public MediaService() {
    super(Media.class);
  }

}
