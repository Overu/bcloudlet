package org.cloudlet.web.core.client;

import com.sencha.gxt.core.client.ValueProvider;

public class StringValueProvider implements ValueProvider<Resource, String> {

  private String propName;

  public StringValueProvider(String propName) {
    this.propName = propName;
  }

  @Override
  public String getPath() {
    return propName;
  }

  @Override
  public String getValue(Resource resource) {
    Object value = resource.getValue(propName);
    if (value instanceof String || value == null) {
      return (String) value;
    }
    return value.toString();
  }

  @Override
  public void setValue(Resource resource, String value) {
    resource.setString(propName, value);
  }

}
