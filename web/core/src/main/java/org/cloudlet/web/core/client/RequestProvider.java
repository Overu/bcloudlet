package org.cloudlet.web.core.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceProxy;

public abstract class RequestProvider extends RequestBuilder {

  public static class Callback<E extends Resource> {

    public void onError(final Throwable exception) {
    }

    public void onSuccess(final E resource) {
    }

    public void onSuccess(final JSONObject json) {
    }

  }

  public static class DELETE extends ResponseMethod {
    public DELETE(final String url) {
      super(DELETE, url);
    }
  }

  public static class GET extends ResponseMethod {
    public GET(final String url) {
      super(GET, url);
    }
  }

  public static class POST extends RequestMethod {
    public POST(final String url) {
      super(POST, url);
    }
  }

  public static class PUT extends RequestMethod {
    public PUT(final String url) {
      super(PUT, url);
    }
  }

  public static class RequestMethod extends RequestProvider {
    public RequestMethod(final Method httpMethod, final String url) {
      super(httpMethod, url);
    }

    public RequestMethod requestData(final JSONObject json, final String objectType) {
      json.put("@xsi.type", new JSONString(objectType));
      return requestData("{\"dataGraph\":{\"root\":" + json.toString() + "}}");
    }

    public RequestMethod requestData(final String requestData) {
      super.setRequestData(requestData);
      return this;
    }
  }

  public static class ResponseMethod extends RequestProvider {
    public ResponseMethod(final Method httpMethod, final String url) {
      super(httpMethod, url);
    }

  }

  private static Resource place;

  private static ResourceProxy placeProxy;

  private static final Callback<Resource> DEFAULT = new Callback<Resource>();

  public static DELETE DELETE(final String url) {
    return new DELETE(url);
  }

  public static GET GET(final String url) {
    return new GET(url);
  }

  public static POST POST(final String url) {
    return new POST(url);
  }

  public static PUT PUT(final String url) {
    return new PUT(url);
  }

  private RequestProvider(final Method httpMethod, final String url) {
    super(httpMethod, url);
  }

  public RequestProvider accept(final String value) {
    return header("Accept", value);
  }

  public <T extends Resource> RequestProvider callback(final Callback<T> callback) {
    super.setCallback(new RequestCallback() {

      @Override
      public void onError(final Request request, final Throwable exception) {
        callback.onError(exception);
      }

      @Override
      public void onResponseReceived(final Request request, final Response response) {
        if (response.getStatusCode() != Response.SC_OK) {
          return;
        }
        String method = RequestProvider.this.getHTTPMethod();
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

  public RequestProvider contentType(final String value) {
    return header("Content-Type", value);
  }

  public RequestProvider place(final Resource place) {
    this.place = place;
    if (place instanceof ResourceProxy) {
      placeProxy = (ResourceProxy) place;
    }
    return this;
  }

  @Override
  public Request send() throws RequestException {
    if (this.getCallback() == null) {
      callback(DEFAULT);
    }
    return super.send();
  }

  private RequestProvider header(final String header, final String value) {
    super.setHeader(header, value);
    return this;
  }
}
