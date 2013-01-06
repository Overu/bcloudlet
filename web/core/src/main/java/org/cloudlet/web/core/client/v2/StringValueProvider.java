package org.cloudlet.web.core.client.v2;

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
    return resource.getString(propName);
  }

  @Override
  public void setValue(Resource resource, String value) {
    resource.setString(propName, value);
  }

}
