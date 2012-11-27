package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Section;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Section.TYPE)
@XmlType(name = Section.TYPE)
@Entity(name = Section.TYPE)
@Table(name = Section.TYPE)
public class SectionBean extends ResourceBean {

  @Override
  public String getResourceType() {
    return Section.TYPE;
  }

}
