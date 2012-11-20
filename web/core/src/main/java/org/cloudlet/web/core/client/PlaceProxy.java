package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

import com.sencha.gxt.data.shared.loader.DataProxy;

import org.cloudlet.web.core.shared.IsResource;
import org.cloudlet.web.core.shared.Resource;

public class PlaceProxy<C extends IsResource> implements DataProxy<C, String> {

  public PlaceProxy() {
  }

  @Override
  public void load(final C loadConfig, final Callback<String, Throwable> callback) {
    try {
      String data = null;
      final StringBuilder url = loadConfig.asResource().getUriBuilder();
      url.append("?" + Resource.CHILDREN + "=true");
      url.insert(0, "api");
      RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url.toString());
      builder.setHeader("Accept", "application/json");
      builder.sendRequest(data, new RequestCallback() {

        @Override
        public void onError(Request request, Throwable exception) {
          callback.onFailure(exception);
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
          if (response.getStatusCode() != Response.SC_OK) {
            callback.onFailure(new RuntimeException("GET " + url.toString()
                + "\r\nInvalid status code " + response.getStatusCode()));
            return;
          }
          callback.onSuccess(response.getText());

        }
      });
    } catch (Exception e) {
      callback.onFailure(e);
    }
  }

}
