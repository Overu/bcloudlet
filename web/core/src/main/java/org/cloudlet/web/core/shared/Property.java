package org.cloudlet.web.core.shared;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Property extends TypedElement {

  private ResourceType declaringType;

  private boolean containment; // TODO set containment

  public ResourceType getDeclaringType() {
    return declaringType;
  }

  public boolean isContainment() {
    return containment;
  }

  public void setContainment(final boolean containment) {
    this.containment = containment;
  }

  public void setDeclaringType(final ResourceType declaringType) {
    this.declaringType = declaringType;
  }
}
