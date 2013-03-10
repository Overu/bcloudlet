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

  private String targetType;

  private String label;

  private long weight;

  public String getTargetType() {
    return targetType;
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

  public long getWeight() {
    return weight;
  }

  public void setTargetType(String type) {
    this.targetType = type;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setWeight(long weight) {
    this.weight = weight;
  }

}
