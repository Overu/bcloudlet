package org.cloudlet.web.core.service;

import org.cloudlet.web.core.MemberFeed;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = MemberFeed.TYPE)
@XmlType(name = MemberFeed.TYPE)
@Entity(name = MemberFeed.TYPE)
@Table(name = MemberFeed.TYPE)
public class MemberFeedBean extends FeedBean<MemberBean> {

  @Override
  @XmlTransient
  public Class<MemberBean> getEntryType() {
    return MemberBean.class;
  }

  @Override
  public String getResourceType() {
    return MemberFeed.TYPE;
  }

}
