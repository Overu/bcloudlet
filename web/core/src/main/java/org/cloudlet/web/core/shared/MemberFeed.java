package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = MemberFeed.TYPE_NAME)
@XmlType(name = MemberFeed.TYPE_NAME)
@Entity
@Handler(MemberFeedService.class)
public class MemberFeed extends Feed<Member> {
  public static final String TYPE_NAME = "MemberFeed";
  public static FeedType<MemberFeed, Member> TYPE = new FeedType<MemberFeed, Member>(Feed.TYPE,
      TYPE_NAME, Member.TYPE) {
    @Override
    public MemberFeed createInstance() {
      return new MemberFeed();
    }
  };

  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }

  @Override
  public FeedType<MemberFeed, Member> getResourceType() {
    return TYPE;
  }
}
