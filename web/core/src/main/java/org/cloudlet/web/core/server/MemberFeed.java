package org.cloudlet.web.core.server;


import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.MEMBER_FEED)
@XmlType(name = CorePackage.MEMBER_FEED)
@Entity(name = CorePackage.MEMBER_FEED)
@Table(name = CorePackage.MEMBER_FEED)
public class MemberFeed extends Feed<Member> {
  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.MEMBER_FEED;
  }

}
