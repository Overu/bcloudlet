package org.cloudlet.web.core.shared;

import com.google.gwt.user.client.TakesValue;

public class WebView implements TakesValue<Resource> {

  protected Resource resource;

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource resource) {
    this.resource = resource;
  }

}
