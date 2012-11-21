package org.cloudlet.web.core.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import org.cloudlet.web.core.shared.User;

public class JSONUserProvider extends JSONResourceProvider<User> {
  @Override
  public void read(User resource, JSONObject json) {
    super.read(resource, json);
    resource.setEmail(readString(json, User.EMAIL));
    resource.setName(readString(json, User.NAME));
  }

  @Override
  public JSONObject write(User model) {
    JSONObject json = super.write(model);
    String email = model.getEmail();
    if (email != null) {
      json.put(User.EMAIL, new JSONString(email));
    }
    return json;
  }

}
