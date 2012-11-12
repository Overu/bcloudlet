package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = SectionFeed.TYPE_NAME)
@Entity
@Handler(GroupFeedService.class)
@Path("sections")
@DefaultField(key = "title", value = "章节")
public class SectionFeed extends PagingFeed<Section> {

  public static final String TYPE_NAME = "SectionFeed";

  public static FeedType<SectionFeed, Section> TYPE = new FeedType<SectionFeed, Section>(
      PagingFeed.TYPE, TYPE_NAME, Section.TYPE) {
    @Override
    public SectionFeed createInstance() {
      return new SectionFeed();
    }
  };

  @Override
  public Class<Section> getEntryType() {
    return Section.class;
  }

  @Override
  public FeedType<SectionFeed, Section> getResourceType() {
    return TYPE;
  }
}
