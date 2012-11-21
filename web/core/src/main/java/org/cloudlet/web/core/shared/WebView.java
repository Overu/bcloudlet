package org.cloudlet.web.core.shared;

import com.google.gwt.user.client.TakesValue;

public class WebView<T extends Resource> implements TakesValue<T> {

  protected T resource;

  @Override
  public T getValue() {
    return resource;
  }

  @Override
  public void setValue(T resource) {
    this.resource = resource;
  }

}
