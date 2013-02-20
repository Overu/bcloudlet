package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class SectionService extends EntryService<Section> {

  public SectionService() {
    super(Section.class);
  }

}
