package org.cloudlet.web.core.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import org.cloudlet.web.core.shared.Resource;

public class JSONResourceProvider<T extends Resource> implements JSONObjectProvider<T> {

  @Override
  public T read(Object loadConfig, JSONObject data) {
    return null;
  }

  @Override
  public JSONObject write(T model) {
    JSONObject json = new JSONObject();
    json.put("@xsi.type", new JSONString(model.getResourceType().getName()));
    if (model.getId() != null) {
      json.put("id", new JSONString(model.getId()));
    }
    if (model.getPath() != null) {
      json.put("path", new JSONString(model.getPath()));
    }
    if (model.getTitle() != null) {
      json.put("title", new JSONString(model.getTitle()));
    }
    return json;
  }
}
