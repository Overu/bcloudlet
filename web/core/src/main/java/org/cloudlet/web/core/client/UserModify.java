package org.cloudlet.web.core.client;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.widget.core.client.event.SelectEvent;

import org.cloudlet.web.core.client.RequestProvider.Callback;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.User;

public class UserModify extends AbstractUserFieldView {

  public UserModify() {
    super("User Modify");
  }

  @Override
  protected void onAttach(final AttachEvent event) {
    try {
      RequestProvider.GET("api" + placeManager.getWhere().getUri()).accept(
          RequestFactory.JSON_CONTENT_TYPE_UTF8).callback(new Callback<User>() {
        @Override
        public void onSuccess(JSONObject json) {
          initJSON(json);
        }
      }).send();
    } catch (RequestException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void selectHandler(final SelectEvent event) {
    try {
      RequestProvider.PUT("api" + placeManager.getWhere().getUri()).requestData(initJSON(null),
          User.TYPE_NAME).contentType(RequestFactory.JSON_CONTENT_TYPE_UTF8).callback(
          new Callback<User>() {
            @Override
            public void onSuccess(JSONObject json) {
              placeManager.goTo(place.getParent().getParent(), Feed.LIST_WIDGET);
            }
          }).send();
    } catch (RequestException e) {
      e.printStackTrace();
    }
  }
}