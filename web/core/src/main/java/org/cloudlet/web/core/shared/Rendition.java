package org.cloudlet.web.core.shared;

import java.util.List;

public class Rendition extends Resource {

  @Override
  public Rendition getRendition() {
    return this;
  }

  @Override
  public Rendition getRendition(String kind) {
    throw new UnsupportedOperationException();
  }

  @Override
  public StringBuilder getUriBuilder() {
    StringBuilder builder = getParent().getUriBuilder();
    builder.append("?").append(Resource.RENDITION).append("=").append(getPath());
    for (String key : getQueryParameters().keySet()) {
      if (Resource.RENDITION.equals(key)) {
        continue;
      }
      List<String> values = getQueryParameters().get(key);
      for (String value : values) {
        builder.append("&").append(key).append("=").append(value);
      }
    }
    return builder;
  }
}
