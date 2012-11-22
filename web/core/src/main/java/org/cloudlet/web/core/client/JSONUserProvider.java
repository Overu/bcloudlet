package org.cloudlet.web.core.client;

import com.google.gwt.json.client.JSONObject;

import org.cloudlet.web.core.shared.User;

public class JSONUserProvider extends JSONResourceProvider<User> {

  @Override
  public void read(User resource, JSONObject json) {
    super.read(resource, json);
    resource.setEmail(readString(json, User.EMAIL));
    resource.setName(readString(json, User.NAME));
    resource.setPhone(readString(json, User.PHONE));
    resource.setState(readString(json, User.STATE));
    resource.setZip(readString(json, User.ZIP));
  }

  @Override
  public JSONObject write(User model) {
    JSONObject json = super.write(model);
    writeString(json, User.EMAIL, model.getEmail());
    writeString(json, User.NAME, model.getName());
    writeString(json, User.PHONE, model.getPhone());
    writeString(json, User.STATE, model.getState());
    writeString(json, User.ZIP, model.getZip());
    return json;
  }

}
