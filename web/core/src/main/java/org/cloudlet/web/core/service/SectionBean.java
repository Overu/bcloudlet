package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = SectionBean.TYPE)
@XmlType(name = SectionBean.TYPE)
@Entity(name = SectionBean.TYPE)
@Table(name = SectionBean.TYPE)
public class SectionBean extends ResourceBean {
  public static final String TYPE = CorePackage.PREFIX + "Section";

  @Override
  public String getResourceType() {
    return TYPE;
  }

}
