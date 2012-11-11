package org.cloudlet.web.core.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.widget.core.client.event.SelectEvent;

public class UserFrom extends AbstractUserFieldView {

  public UserFrom() {
    super("User From");
  }

  @Override
  protected RequestBuilder selectHandler(final SelectEvent event) {
    return initRequestBuilder(RequestBuilder.POST, "api/users", "Content-Type",
        RequestFactory.JSON_CONTENT_TYPE_UTF8, new Responsecallback() {

          @Override
          public void completed(final String text) {
            placeManager.goTo(place);
          }
        });
  }
}
