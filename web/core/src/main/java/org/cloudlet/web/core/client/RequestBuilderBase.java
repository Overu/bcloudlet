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

public class RequestBuilderBase extends RequestBuilder {

  public static class Callback<E extends Resource> {

    public void onError(Throwable exception) {
    }

    public void onSuccess(E resource) {
    }

    public void onSuccess(JSONObject json) {
    }

  }

  public static class DELETE extends ResponseMethod {
    public DELETE(String url) {
      super(DELETE, url);
    }
  }

  public static class GET extends ResponseMethod {
    public GET(String url) {
      super(GET, url);
    }
  }

  public static class POST extends RequestMethod {
    public POST(String url) {
      super(POST, url);
    }
  }

  public static class PUT extends RequestMethod {
    public PUT(String url) {
      super(PUT, url);
    }
  }

  public static class RequestMethod extends RequestBuilderBase {
    public RequestMethod(Method httpMethod, String url) {
      super(httpMethod, url);
    }

    public RequestMethod bindRequestData(JSONObject json, String objectType) {
      json.put("@xsi.type", new JSONString(objectType));
      return bindRequestData("{\"dataGraph\":{\"root\":" + json.toString() + "}}");
    }

    public RequestMethod bindRequestData(String requestData) {
      super.setRequestData(requestData);
      return this;
    }

    public RequestMethod ContentType(String value) {
      return (RequestMethod) super.bindHeader("Content-Type", value);
    }
  }

  public static class ResponseMethod extends RequestBuilderBase {
    public ResponseMethod(Method httpMethod, String url) {
      super(httpMethod, url);
    }

    public ResponseMethod Accept(String value) {
      return (ResponseMethod) super.bindHeader("Accept", value);
    }
  }

  private static Resource place;

  private static ResourceProxy placeProxy;

  public static DELETE DELETE(String url) {
    return new DELETE(url);
  }

  public static GET GET(String url) {
    return new GET(url);
  }

  public static POST POST(String url) {
    return new POST(url);
  }

  public static PUT PUT(String url) {
    return new PUT(url);
  }

  private RequestBuilderBase(Method httpMethod, String url) {
    super(httpMethod, url);
  }

  public <T extends Resource> RequestBuilderBase bindCallback(final Callback<T> callback) {
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
        String method = RequestBuilderBase.this.getHTTPMethod();
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

  public RequestBuilderBase bindPlace(Resource place) {
    this.place = place;
    if (place != null || place instanceof ResourceProxy) {
      placeProxy = (ResourceProxy) place;
    }
    return this;
  }

  private RequestBuilderBase bindHeader(String header, String value) {
    super.setHeader(header, value);
    return this;
  }
}
