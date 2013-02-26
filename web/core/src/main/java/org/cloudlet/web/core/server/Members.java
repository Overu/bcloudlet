package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.Members)
@XmlType(name = CorePackage.Members)
@Entity(name = CorePackage.Members)
@Table(name = CorePackage.Members)
public class Members extends Feed<Member> {
  @Override
  @XmlTransient
  public Class<Member> getEntryType() {
    return Member.class;
  }

  @Override
  public String getResourceType() {
    return CorePackage.Members;
  }

}
