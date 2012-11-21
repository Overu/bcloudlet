package org.cloudlet.web.core.shared;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class ResourceType<T extends Resource> extends WebType<T> {

  private Map<String, Object> widgets = new HashMap<String, Object>();

  public static String OPERATIONS = "operations";

  public static String PROPERTIES = "properties";

  private boolean _abstract;

  protected ResourceType superType;

  private transient Map<String, Property> properties = new HashMap<String, Property>();

  private transient Map<String, Property> allProperties;

  private transient Map<String, Operation> operations = new HashMap<String, Operation>();

  private transient Map<String, Operation> allOperations;

  private transient Map<Class<?>, Object> providers = new HashMap<Class<?>, Object>();

  public ResourceType() {
  }

  public ResourceType(ResourceType superType, String name) {
    this.superType = superType;
    setName(name);
    WebPlatform.getInstance().addType(this);
  }

  public ResourceType(String name) {
    this(null, name);
  }

  public void addOperation(Operation operation) {
    operations.put(operation.getName(), operation);
  }

  public void addProperty(Property property) {
    properties.put(property.getName(), property);
  }

  public T createInstance() {
    throw new UnsupportedOperationException();
  }

  public Map<String, Operation> getAllOperations() {
    if (allOperations == null) {
      allOperations = new HashMap<String, Operation>();
      allOperations.putAll(operations);
      if (superType != null) {
        allOperations.putAll(superType.getAllOperations());
      }
    }
    return allOperations;
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

  public Operation getOperation(String name) {
    Operation operation = operations.get(name);
    if (operation == null && superType != null) {
      operation = superType.getOperation(name);
    }
    return operation;
  }

  public Map<String, Property> getProperties() {
    return properties;
  }

  public Property getProperty(String name) {
    Property result = properties.get(name);
    return result;
  }

  public <T> T getProvider(Class<T> cls) {
    T provider = (T) providers.get(cls);
    if (provider == null && superType != null) {
      provider = (T) superType.getProvider(cls);
    }
    return provider;
  }

  @Override
  public String getQualifiedName() {
    return getPackage().getName() + "." + getName();
  }

  public Set<String> getRenditionKinds() {
    Set<String> result = new HashSet<String>();
    result.addAll(widgets.keySet());
    if (superType != null) {
      result.addAll(superType.getRenditionKinds());
    }
    result.add(Resource.HOME);
    return result;
  }

  public ResourceType getSuperType() {
    return superType;
  }

  public Object getWidget(String key) {
    Object result = widgets.get(key);
    if (result == null && superType != null) {
      result = superType.getWidget(key);
    }
    return result;
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

  public void setAbstract(boolean value) {
    this._abstract = value;
  }

  public <T> void setProvider(Class<T> cls, T instnace) {
    providers.put(cls, instnace);
  }

  public void setSuperType(ResourceType superType) {
    this.superType = superType;
  }

  public void setWidget(String key, Object widget) {
    widgets.put(key, widget);
  }

}
