package org.cloudlet.web.core.bean;

import javax.xml.bind.annotation.XmlType;

@XmlType
public abstract class TypedElement extends NamedElement {

  protected WebType targetType;

  private boolean many;

  public WebType getTargetType() {
    return targetType;
  }

  public boolean isMany() {
    return many;
  }

  public void setMany(final boolean many) {
    this.many = many;
  }

  public void setTargetType(final WebType type) {
    this.targetType = type;
  }

}
