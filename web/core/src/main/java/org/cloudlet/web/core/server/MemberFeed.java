package org.cloudlet.web.core.server;


import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.MemberFeed)
@XmlType(name = CorePackage.MemberFeed)
@Entity(name = CorePackage.MemberFeed)
@Table(name = CorePackage.MemberFeed)
public class MemberFeed extends Feed<Member> {
  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.MemberFeed;
  }

}
