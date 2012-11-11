package org.cloudlet.web.core.shared;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Package extends NamedElement {

  private transient Map<String, WebType> types = new HashMap<String, WebType>();

  protected Package() {
  }

  public void addType(final WebType type) {
    types.put(type.getName(), type);
  }

  public WebType getType(final String name) {
    WebType type = types.get(name);
    return type;
  }

  protected void addContentTypes(final ResourceType... types) {
    for (ResourceType type : types) {
      type.setPackage(this);
      addType(type);
    }
  }

  protected <T> void addProperty(final ResourceType entityType, final String name,
      final WebType type, final boolean many) {
    Property property = new Property();
    property.setName(name);
    property.setType(type);
    property.setMany(many);
    entityType.addProperty(property);
  }

  protected void init() {
    WebPlatform.getInstance().getPackages().put(this.getName(), this);
  }

}
