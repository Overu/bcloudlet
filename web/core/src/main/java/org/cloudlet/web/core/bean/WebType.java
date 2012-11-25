package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.shared.Package;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class WebType<T> extends NamedElement {

  protected transient Class<T> javaClass;

  private Package _package;

  public Class<T> getJavaClass() {
    return javaClass;
  }

  public Package getPackage() {
    return _package;
  }

  public String getQualifiedName() {
    return getPackage().getName() + "." + getName();
  }

  public void setJavaClass(final Class<T> javaClass) {
    this.javaClass = javaClass;
  }

  public void setPackage(final Package _package) {
    this._package = _package;
  }

}
