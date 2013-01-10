package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = MemberFeedBean.TYPE)
@XmlType(name = MemberFeedBean.TYPE)
@Entity(name = MemberFeedBean.TYPE)
@Table(name = MemberFeedBean.TYPE)
public class MemberFeedBean extends FeedBean<MemberBean> {
  public static final String TYPE = CorePackage.PREFIX + "MemberFeed";

  @Override
  @XmlTransient
  public Class<MemberBean> getEntryType() {
    return MemberBean.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

}
