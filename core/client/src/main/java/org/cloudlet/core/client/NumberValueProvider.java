package org.cloudlet.core.client;

import com.sencha.gxt.core.client.ValueProvider;

public class NumberValueProvider<T extends Number> implements ValueProvider<Resource, T> {

  protected String propName;

  public NumberValueProvider(String propName) {
    this.propName = propName;
  }

  @Override
  public String getPath() {
    return propName;
  }

  @Override
  public T getValue(Resource resource) {
    return (T) resource.getValue(propName);
  }

  @Override
  public void setValue(Resource resource, T value) {
    resource.setValue(propName, value);
  }

}
