package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.writer.DataWriter;

import org.cloudlet.web.core.shared.Resource;

public class ResourceProxy<T extends Resource> implements DataProxy<T, T> {

  private DataWriter<T, String> writer;

  public void delete(final T resource, final Callback<T, Throwable> callback) {
    execute(RequestBuilder.DELETE, resource, callback);
  }

  public void execute(RequestBuilder.Method method, final T resource,
      final Callback<T, Throwable> callback) {
    try {
      String data = null;
      if (RequestBuilder.POST.equals(method) || RequestBuilder.PUT.equals(method)) {
        data = generateData(resource);
      }
      final StringBuilder url = resource.getUriBuilder();
      url.insert(0, "api");
      RequestBuilder builder = new RequestBuilder(method, url.toString());
      builder.setHeader("Accept", "application/json");
      if (RequestBuilder.POST.equals(method) || RequestBuilder.PUT.equals(method)) {
        builder.setHeader("Content-Type", "application/json");
      }
      builder.sendRequest(data, new RequestCallback() {

        @Override
        public void onError(Request request, Throwable exception) {
          callback.onFailure(exception);
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
          if (response.getStatusCode() == Response.SC_OK) {
            JSONObject data = JSONParser.parseLenient(response.getText()).isObject();
            JSONResourceProvider.readRoot(resource, data);
            callback.onSuccess(resource);
          } else {
            callback.onFailure(new RuntimeException("GET " + url.toString()
                + "\r\nInvalid status code " + response.getStatusCode()));
          }
        }
      });
    } catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public DataWriter<T, String> getWriter() {
    return writer;
  }

  @Override
  public void load(final T resource, final Callback<T, Throwable> callback) {
    execute(RequestBuilder.GET, resource, callback);
  }

  public void post(final T resource, final Callback<T, Throwable> callback) {
    execute(RequestBuilder.POST, resource, callback);
  }

  public void put(final T resource, final Callback<T, Throwable> callback) {
    execute(RequestBuilder.PUT, resource, callback);
  }

  public void setWriter(DataWriter<T, String> writer) {
    this.writer = writer;
  }

  protected String generateData(T res) {
    JSONObject json = JSONResourceProvider.writeResource(res);
    return json.toString();
  }

}
