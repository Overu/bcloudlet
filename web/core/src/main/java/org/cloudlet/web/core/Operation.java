package org.cloudlet.web.core;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Operation extends TypedElement {

  private String httpMethod;

  private transient Object javaMethod;

  private transient Map<String, Parameter> parameters = new LinkedHashMap<String, Parameter>();

  private ObjectType declaringType;

  private transient String qualifiedName;

  public ObjectType getDeclaringType() {
    return declaringType;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  /**
   * @return the javaMethod
   */
  public <T> T getJavaMethod() {
    return (T) javaMethod;
  }

  public Parameter getParameter(String name) {
    Parameter result = parameters.get(name);
    return result;
  }

  public Map<String, Parameter> getParameters() {
    return parameters;
  }

  public String getQualifiedName() {
    if (qualifiedName == null) {
      ObjectType type = getDeclaringType();
      qualifiedName = type.getQualifiedName() + "." + getName();
    }
    return qualifiedName;
  }

  public void setDeclaringType(ObjectType containingType) {
    this.declaringType = containingType;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public void setJavaMethod(Object javaMethod) {
    this.javaMethod = javaMethod;
  }

}
