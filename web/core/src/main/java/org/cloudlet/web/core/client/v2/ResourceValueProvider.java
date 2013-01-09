package org.cloudlet.web.core.client.v2;

import com.sencha.gxt.core.client.ValueProvider;

public class ResourceValueProvider implements ValueProvider<Resource, Resource> {

  private String path;

  public ResourceValueProvider(String path) {
    this.path = path;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public Resource getValue(Resource object) {
    return object.getResource(path);
  }

  @Override
  public void setValue(Resource object, Resource value) {
    object.setValue(path, value);
  }
}
