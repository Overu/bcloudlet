package org.cloudlet.web.core;

import javax.xml.bind.annotation.XmlType;

@XmlType
public abstract class NamedElement extends Entry {

  public static final String NAME = "name";

  protected String name;

  public String getName() {
    return name;
  }

  public void setName(final String value) {
    this.name = value;
  }

  @Override
  public String toString() {
    return name;
  }
}
