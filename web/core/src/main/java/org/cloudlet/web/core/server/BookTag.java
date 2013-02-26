package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = CorePackage.BookTag)
@XmlType(name = CorePackage.BookTag)
@Entity(name = CorePackage.BookTag)
@Table(name = CorePackage.BookTag)
public class BookTag extends Entry {

  @Override
  public String getResourceType() {
    return CorePackage.BookTag;
  }

}
