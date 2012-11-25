package org.cloudlet.web.core.shared;

import org.cloudlet.web.core.bean.NamedElement;
import org.cloudlet.web.core.bean.Property;
import org.cloudlet.web.core.bean.ResourceType;
import org.cloudlet.web.core.bean.WebPlatform;
import org.cloudlet.web.core.bean.WebType;

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

  protected <T> void addProperty(final ResourceType entityType, final String name,
      final WebType type, final boolean many) {
    Property property = new Property();
    property.setName(name);
    property.setTargetType(type);
    property.setMany(many);
    entityType.addProperty(property);
  }

  protected void addResourceType(final ResourceType... types) {
    for (ResourceType type : types) {
      type.setPackage(this);
      addType(type);
    }
  }

  protected void init() {
    WebPlatform.getInstance().getPackages().put(this.getName(), this);
  }

}
