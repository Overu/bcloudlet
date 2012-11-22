package org.cloudlet.web.core.shared;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class WebView<T extends Resource> implements TakesValue<T>, IsWidget {

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
