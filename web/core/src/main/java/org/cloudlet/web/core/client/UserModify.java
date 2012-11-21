package org.cloudlet.web.core.client;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.widget.core.client.event.SelectEvent;

import org.cloudlet.web.core.client.RequestProvider.Callback;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;

public class UserModify extends AbstractUserFieldView {

  @Inject
  ResourceProxy<User> proxy;

  @Inject
  public UserModify() {
    super("User Modify");
  }

  @Override
  public void setValue(Resource resource) {
    super.setValue(resource);
    JSONObject object = resource.getNativeData();
    initJSON(object);
  }

  @Override
  protected void selectHandler(final SelectEvent event) {
    try {
      RequestProvider.PUT("api" + placeManager.getWhere().getUri()).requestData(initJSON(null),
          User.TYPE_NAME).contentType(RequestFactory.JSON_CONTENT_TYPE_UTF8).callback(
          new Callback<User>() {
            @Override
            public void onSuccess(final JSONObject json) {
              placeManager.goTo(resource.getParent().getParent().getRendition(UserFeed.LIST));
            }
          }).send();
    } catch (RequestException e) {
      e.printStackTrace();
    }
  }

}
