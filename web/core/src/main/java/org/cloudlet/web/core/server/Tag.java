package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Tag.TYPE_NAME)
public class Tag extends Entry {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Tag";

  private String contentType;

  private String label;

  public String getContentType() {
    return contentType;
  }

  public String getLabel() {
    return label;
  }

  @Override
  @XmlTransient
  public Class<? extends Service> getServiceType() {
    return TagService.class;
  }

  @Override
  public String getType() {
    return TYPE_NAME;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
