package org.cloudlet.web.core.server;


import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.SECTION)
@XmlType(name = CorePackage.SECTION)
@Entity(name = CorePackage.SECTION)
@Table(name = CorePackage.SECTION)
public class Section extends Resource {
  @Override
  public String getResourceType() {
    return CorePackage.SECTION;
  }

}
