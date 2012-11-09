package org.cloudlet.web.core;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class ObjectType extends WebType {

  public static ObjectType VARIABLE = new ObjectType();

  public static final String OPERATIONS = "operations";

  public static final String PROPERTIES = "properties";

  private boolean _abstract;

  private ObjectType superType;

  private transient Map<String, Property> properties = new HashMap<String, Property>();

  private transient Map<String, Property> allProperties;

  private transient Map<String, Operation> operations = new HashMap<String, Operation>();

  public void addOperation(final Operation operation) {
    operations.put(operation.getName(), operation);
  }

  public void addProperty(final Property property) {
    properties.put(property.getName(), property);
  }

  public Map<String, Property> getAllProperties() {
    if (allProperties == null) {
      allProperties = new HashMap<String, Property>();
      allProperties.putAll(properties);
      if (superType != null) {
        allProperties.putAll(superType.getAllProperties());
      }
    }
    return allProperties;
  }

  public Operation getOperation(final String name) {
    Operation operation = operations.get(name);
    if (operation == null && superType != null) {
      operation = superType.getOperation(name);
    }
    return operation;
  }

  public Map<String, Property> getProperties() {
    return properties;
  }

  public Property getProperty(final String name) {
    Property result = properties.get(name);
    return result;
  }

  @Override
  public String getQualifiedName() {
    return getPackage().getName() + "." + getName();
  }

  public ObjectType getSuperType() {
    return superType;
  }

  public boolean isAbstract() {
    return _abstract;
  }

  public void setAbstract(final boolean value) {
    this._abstract = value;
  }

  public void setSuperType(final ObjectType superType) {
    this.superType = superType;
  }

}
