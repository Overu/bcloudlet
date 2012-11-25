package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.MemberFeedService;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = MemberFeedBean.TYPE_NAME)
@XmlType(name = MemberFeedBean.TYPE_NAME)
@Entity
@Handler(MemberFeedService.class)
public class MemberFeedBean extends FeedBean<MemberBean> {
  public static final String TYPE_NAME = "MemberFeed";
  public static FeedType<MemberFeedBean, MemberBean> TYPE = new FeedType<MemberFeedBean, MemberBean>(FeedBean.TYPE,
      TYPE_NAME, MemberBean.TYPE) {
    @Override
    public MemberFeedBean createInstance() {
      return new MemberFeedBean();
    }
  };

  @Override
  @XmlTransient
  public Class<MemberBean> getEntryType() {
    return MemberBean.class;
  }

  @Override
  public FeedType<MemberFeedBean, MemberBean> getResourceType() {
    return TYPE;
  }
}
