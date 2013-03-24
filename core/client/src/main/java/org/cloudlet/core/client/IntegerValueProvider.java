package org.cloudlet.core.client;

public class IntegerValueProvider extends NumberValueProvider<Integer> {

  public IntegerValueProvider(String propName) {
    super(propName);
  }

  @Override
  public Integer getValue(Resource resource) {
    return ((Double) resource.getValue(propName)).intValue();
  }

}
