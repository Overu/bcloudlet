package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.BookTag)
@XmlType(name = CorePackage.BookTag)
@Entity(name = CorePackage.BookTag)
@Table(name = CorePackage.BookTag)
public class BookTag extends Entry {

  private String label;

  public String getLabel() {
    return label;
  }

  @Override
  public String getResourceType() {
    return CorePackage.BookTag;
  }

  @Override
  @XmlTransient
  public Class<? extends Service> getServiceType() {
    return BookTagService.class;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
