package org.cloudlet.web.core.server;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity(name = Tag.TYPE_NAME)
public class Tag extends Item {

  public static final String TYPE_NAME = CoreUtil.PREFIX + "Tag";

  private String targetType;

  private String value;

  private long weight;

  public String getTargetType() {
    return targetType;
  }

  @Override
  public String getType() {
    return TYPE_NAME;
  }

  public String getValue() {
    return value;
  }

  public long getWeight() {
    return weight;
  }

  public void setTargetType(String type) {
    this.targetType = type;
  }

  public void setValue(String label) {
    this.value = label;
  }

  public void setWeight(long weight) {
    this.weight = weight;
  }

}
