package org.cloudlet.web.core.shared;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class ResourceType<T extends Resource> extends WebType<T> {

  public static final String OPERATIONS = "operations";

  public static final String PROPERTIES = "properties";

  private boolean _abstract;

  protected ResourceType superType;

  private transient Map<String, Property> properties = new HashMap<String, Property>();

  private transient Map<String, Property> allProperties;

  private transient Map<String, Operation> operations = new HashMap<String, Operation>();

  public ResourceType() {
  }

  public ResourceType(ResourceType superType, String name) {
    this.superType = superType;
    this.name = name;
    WebPlatform.getInstance().addType(this);
  }

  public ResourceType(String name) {
    this(null, name);
  }

  public void addOperation(final Operation operation) {
    operations.put(operation.getName(), operation);
  }

  public void addProperty(final Property property) {
    properties.put(property.getName(), property);
  }

  public T createInstance() {
    throw new UnsupportedOperationException();
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

  @Override
  public Property getProperty(final String name) {
    Property result = properties.get(name);
    return result;
  }

  @Override
  public String getQualifiedName() {
    return getPackage().getName() + "." + getName();
  }

  public ResourceType getSuperType() {
    return superType;
  }

  public boolean hasSuperType(ResourceType type) {
    if (type == this) {
      return true;
    }
    if (superType == null) {
      return false;
    }
    return superType.hasSuperType(type);
  }

  public boolean isAbstract() {
    return _abstract;
  }

  public boolean isInstance(Resource content) {
    if (content == null) {
      return false;
    }
    return content.getResourceType().hasSuperType(this);
  }

  public void setAbstract(final boolean value) {
    this._abstract = value;
  }

  public void setSuperType(final ResourceType superType) {
    this.superType = superType;
  }

}
