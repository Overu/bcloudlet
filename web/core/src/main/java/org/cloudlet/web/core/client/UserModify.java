package org.cloudlet.web.core.client;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.widget.core.client.event.SelectEvent;

import org.cloudlet.web.core.shared.Feed;

public class UserModify extends AbstractUserFieldView {

  public UserModify() {
    super("User Modify");
  }

  @Override
  protected void onAttach(final AttachEvent event) {
    // if (place == null) {
    // place = placeManager.getWhere();
    // }
    send(initRequestBuilder(RequestBuilder.GET, "api" + placeManager.getWhere().getUri(), "Accept",
        RequestFactory.JSON_CONTENT_TYPE_UTF8, new Responsecallback() {

          @Override
          public void completed(final String text) {
            initJSON(text, true);
          }
        }), null);
  }

  @Override
  protected RequestBuilder selectHandler(final SelectEvent event) {
    return initRequestBuilder(RequestBuilder.PUT, "api" + placeManager.getWhere().getUri(),
        "Content-Type", RequestFactory.JSON_CONTENT_TYPE_UTF8, new Responsecallback() {
          @Override
          public void completed(final String text) {
            placeManager.goTo(place.getParent().getParent(), Feed.LIST_WIDGET);
          }
        });
  }
}
