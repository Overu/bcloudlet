package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.writer.DataWriter;

import org.cloudlet.web.core.shared.IsResource;
import org.cloudlet.web.core.shared.Resource;

public class ResourceProxy<C extends IsResource> implements DataProxy<C, String> {

  private DataWriter<C, String> writer;

  public DataWriter<C, String> getWriter() {
    return writer;
  }

  @Override
  public void load(final C loadConfig, final Callback<String, Throwable> callback) {
    sendRequest(loadConfig, callback, RequestBuilder.GET);
  }

  public void put(final C loadConfig, final Callback<String, Throwable> callback) {
    sendRequest(loadConfig, callback, RequestBuilder.PUT);
  }

  public void setWriter(DataWriter<C, String> writer) {
    this.writer = writer;
  }

  protected String generateData(C loadConfig) {
    Resource res = loadConfig.asResource();
    JSONObjectProvider<Resource> provider =
        res.getResourceType().getProvider(JSONObjectProvider.class);
    JSONObject json = provider.write(res);
    JSONObject root = new JSONObject();
    root.put("root", json);
    JSONObject dg = new JSONObject();
    dg.put("dataGraph", root);
    return dg.toString();
  }

  protected void onRequest(C config) {
  }

  private void sendRequest(final C loadConfig, final Callback<String, Throwable> callback,
      RequestBuilder.Method method) {
    try {
      String data = null;
      if (RequestBuilder.POST.equals(method) || RequestBuilder.PUT.equals(method)) {
        data = generateData(loadConfig);
      }
      onRequest(loadConfig);
      final StringBuilder url = loadConfig.asResource().getRendition().getUriBuilder();
      url.insert(0, "api");
      RequestBuilder builder = new RequestBuilder(method, url.toString());
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
