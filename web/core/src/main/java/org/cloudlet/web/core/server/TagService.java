package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

@Singleton
public class TagService extends FeedService<Tags, Tag> {

  public TagService() {
    super(Tags.class, Tag.class);
  }

  @Override
  public Content createReference(Tag source, Content target) {
    Content result = super.createReference(source, target);
    source.setTotal(source.getTotal() + 1);
    update(source);
    return result;
  }
}
