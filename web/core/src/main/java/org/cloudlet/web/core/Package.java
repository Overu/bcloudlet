package org.cloudlet.web.core;

import com.google.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Package extends NamedElement {

  private transient Map<String, WebType> types = new HashMap<String, WebType>();

  @Inject
  private static WebPlatform platform;

  protected Package() {
  }

  public void addType(final WebType type) {
    types.put(type.getName(), type);
  }

  public WebType getType(final String name) {
    WebType type = types.get(name);
    return type;
  }

  protected void addEntityTypes(final ObjectType... types) {
    for (ObjectType type : types) {
      type.setPackage(this);
      addType(type);
    }
  }

  protected <T> void addProperty(final ObjectType entityType, final String name,
      final WebType type, final boolean many) {
    Property property = new Property();
    property.setName(name);
    property.setType(type);
    property.setMany(many);
    entityType.addProperty(property);
  }

  protected void init() {
    platform.getPackages().put(this.getName(), this);
  }

}
