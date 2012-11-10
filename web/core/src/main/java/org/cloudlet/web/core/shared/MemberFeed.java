package org.cloudlet.web.core.shared;


import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Handler(MemberFeedService.class)
public class MemberFeed extends Feed<Member> {

  public static FeedType<MemberFeed, Member> TYPE = new FeedType<MemberFeed, Member>(Feed.TYPE,
      "memberFeed", Member.TYPE) {
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
  public FeedType<MemberFeed, Member> getObjectType() {
    return TYPE;
  }
}
