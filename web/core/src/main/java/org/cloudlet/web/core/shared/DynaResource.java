package org.cloudlet.web.core.shared;

import java.util.List;

public class DynaResource extends Resource {

  @Override
  public Resource getByPath(String path) {
    return null;
  }

  @Override
  public StringBuilder getUriBuilder() {
    StringBuilder builder = super.getUriBuilder();
    boolean first = true;
    for (String key : getQueryParameters().keySet()) {
      List<String> values = getQueryParameters().get(key);
      for (String value : values) {
        if (first) {
          first = false;
          builder.append("?");
        } else {
          builder.append("&");
        }
        builder.append(key).append("=").append(value);
      }
    }
    return builder;
  }
}
