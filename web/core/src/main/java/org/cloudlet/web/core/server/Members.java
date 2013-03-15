package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Members.TYPE_NAME)
public class Members extends Folder<Member> {
  public static final String TYPE_NAME = CoreUtil.PREFIX + "Members";

  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }

  @Override
  public String getType() {
    return Members.TYPE_NAME;
  }

}
