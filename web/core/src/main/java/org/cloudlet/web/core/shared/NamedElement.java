package org.cloudlet.web.core.shared;

import javax.xml.bind.annotation.XmlType;

@XmlType
public abstract class NamedElement extends Resource {

  public static final String NAME = "name";

  public String getName() {
    return getPath();
  }

  public void setName(final String value) {
    setPath(value);
  }

  @Override
  public String toString() {
    return getPath();
  }
}
