package org.cloudlet.web.core;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class WebType extends NamedElement {

  protected transient Class<?> javaClass;

  private Package _package;

  public Class<?> getJavaClass() {
    return javaClass;
  }

  public Package getPackage() {
    return _package;
  }

  public String getQualifiedName() {
    return getPackage().getName() + "." + getName();
  }

  public void setJavaClass(final Class<?> javaClass) {
    this.javaClass = javaClass;
  }

  public void setPackage(final Package _package) {
    this._package = _package;
  }

}
