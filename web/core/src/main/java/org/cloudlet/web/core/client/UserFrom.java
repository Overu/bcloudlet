package org.cloudlet.web.core.client;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.widget.core.client.event.SelectEvent;

import org.cloudlet.web.core.client.RequestBuilderBase.Callback;
import org.cloudlet.web.core.shared.User;

public class UserFrom extends AbstractUserFieldView {

  public UserFrom() {
    super("User From");
  }

  @Override
  protected void selectHandler(final SelectEvent event) {
    try {
      RequestBuilderBase.POST("api/users").ContentType(RequestFactory.JSON_CONTENT_TYPE_UTF8)
          .bindRequestData(initJSON(null), User.TYPE_NAME).bindCallback(new Callback<User>() {
            @Override
            public void onSuccess(JSONObject json) {
              placeManager.goTo(place);
            }
          }).send();
    } catch (RequestException e) {
      e.printStackTrace();
    }
  }
}
