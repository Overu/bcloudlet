package org.cloudlet.web.core.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceProxy;

public class ExtendsRequestBuilder extends RequestBuilder {

  public static class Callback<E extends Resource> {

    public void onError(Throwable exception) {
    }

    public void onSuccess(E resource) {
    }

    public void onSuccess(JSONObject json) {
    }

  }

  private static ExtendsRequestBuilder INSTANCE;

  private static Resource place;

  private static ResourceProxy placeProxy;

  public static ExtendsRequestBuilder get(Method httpMethod, Resource place, String url) {
    ExtendsRequestBuilder.place = place;
    if (place != null || place instanceof ResourceProxy) {
      placeProxy = (ResourceProxy) place;
    }
    if (INSTANCE == null
        || !(INSTANCE.getUrl().equals(url) && INSTANCE.getHTTPMethod()
            .equals(httpMethod.toString()))) {
      INSTANCE = new ExtendsRequestBuilder(httpMethod, url);
    }
    return INSTANCE;

  }

  public static ExtendsRequestBuilder get(Method httpMethod, String url) {
    return get(httpMethod, null, url);
  }

  private ExtendsRequestBuilder(Method httpMethod, String url) {
    super(httpMethod, url);
  }

  public <T extends Resource> ExtendsRequestBuilder bindCallback(final Callback<T> callback) {
    super.setCallback(new RequestCallback() {

      @Override
      public void onError(Request request, Throwable exception) {
        callback.onError(exception);
      }

      @Override
      public void onResponseReceived(Request request, Response response) {
        if (response.getStatusCode() != Response.SC_OK) {
          return;
        }
        String method = ExtendsRequestBuilder.this.getHTTPMethod();
        Resource resource = null;
        if (GET.toString().equals(method)) {
          JSONObject dg = JSONParser.parseLenient(response.getText()).isObject();
          JSONObject root = dg.get("dataGraph").isObject().get("root").isObject();
          resource = CoreClientModule.readResource(root);
          resource.setParent(placeProxy == null ? resource.getParent() : placeProxy.getParent());
        }
        callback.onSuccess((T) resource);
        callback.onSuccess(resource == null ? null : (JSONObject) resource.getNativeData());

        place = null;
        placeProxy = null;
      }
    });
    return this;
  }

  public ExtendsRequestBuilder bindHeader(String header, String value) {
    super.setHeader(header, value);
    return this;
  }

  public ExtendsRequestBuilder bindRequestData(JSONObject json, String objectType) {
    json.put("@xsi.type", new JSONString(objectType));
    return bindRequestData("{\"dataGraph\":{\"root\":" + json.toString() + "}}");
  }

  public ExtendsRequestBuilder bindRequestData(String requestData) {
    super.setRequestData(requestData);
    return this;
  }
}
