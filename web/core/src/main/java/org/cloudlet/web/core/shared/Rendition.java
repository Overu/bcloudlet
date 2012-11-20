package org.cloudlet.web.core.shared;

import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.annotation.XmlTransient;

public class Rendition extends Resource {

  @XmlTransient
  private MultivaluedMap<String, String> queryParameters;

  @XmlTransient
  public MultivaluedMap<String, String> getQueryParameters() {
    if (queryParameters == null) {
      queryParameters = new MultivaluedHashMap<String, String>();
    }
    return queryParameters;
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
      List<String> values = getQueryParameters().get(key);
      for (String value : values) {
        builder.append("&").append(key).append("=").append(value);
      }
    }
    return builder;
  }

  public void setQueryParameters(MultivaluedMap<String, String> queryParameters) {
    this.queryParameters = queryParameters;
  }

}
