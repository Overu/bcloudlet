package org.cloudlet.web.core.shared;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DynaResource extends Resource {

  Resource delegate;

  @Override
  public Resource getByPath(String path) {
    return delegate == null ? null : delegate.getByPath(path);
  }

  public Resource getDelegate() {
    return delegate;
  }

  @Override
  public Resource getRelationship(Property prop) {
    return delegate == null ? null : delegate.getRelationship(prop);
  }

  @Override
  public Resource getRendition(String kind) {
    return delegate == null ? null : delegate.getRendition(kind);
  }

  @Override
  public Map<String, Resource> getRenditions() {
    return delegate == null ? Collections.EMPTY_MAP : delegate.getRenditions();
  }

  @Override
  public ResourceType<? extends Resource> getResourceType() {
    return delegate.getResourceType();
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

  public void setDelegate(Resource delegate) {
    this.delegate = delegate;
  }
}
