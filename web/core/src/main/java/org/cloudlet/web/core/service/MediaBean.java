package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Media;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = Media.TYPE)
@XmlType(name = Media.TYPE)
@Entity(name = Media.TYPE)
@Table(name = Media.TYPE)
public class MediaBean extends ResourceBean {

  @Override
  public String getType() {
    return Media.TYPE;
  }

}
