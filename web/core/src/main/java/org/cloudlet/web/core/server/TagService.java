package org.cloudlet.web.core.server;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Singleton
public class TagService extends FeedService<Tags, Tag> {

  public TagService() {
    super(Tags.class, Tag.class);
  }

  @Transactional
  public void addTag(Taggable taggable, Tag tag) {
    Set<Tag> tags = taggable.getTags();
    if (tags == null) {
      tags = new HashSet<Tag>();
    }
    tags.add(tag);
    taggable.setTags(tags);
    taggable.update();

    tag.setWeight(tag.getWeight() + 1);
    tag.update();
  }

  public Tag getOrCreateTag(String value, String targetType) {
    try {
      TypedQuery<Tag> query = em().createQuery(" from " + Tag.TYPE_NAME + " t where t.targetType=:type and t.value=:value", Tag.class);
      return query.getSingleResult();
    } catch (NoResultException e) {
      Tag tag = new Tag();
      tag.setValue(value);
      tag.setTargetType(targetType);
      createEntry(tag);
      return tag;
    }
  }

}
