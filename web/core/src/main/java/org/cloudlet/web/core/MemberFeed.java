package org.cloudlet.web.core;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = MemberFeed.TYPE)
@XmlType(name = MemberFeed.TYPE)
@Entity(name = MemberFeed.TYPE)
@Table(name = MemberFeed.TYPE)
public class MemberFeed extends Feed<Member> {
  public static final String TYPE = CorePackage.PREFIX + "MemberFeed";

  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

}
