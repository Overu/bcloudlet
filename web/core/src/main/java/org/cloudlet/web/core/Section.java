package org.cloudlet.web.core;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Section.TYPE)
@XmlType(name = Section.TYPE)
@Entity(name = Section.TYPE)
@Table(name = Section.TYPE)
public class Section extends Content {
  public static final String TYPE = CorePackage.PREFIX + "Section";

  @Override
  public String getResourceType() {
    return TYPE;
  }

}
