package org.cloudlet.web.core.client.v2;

import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.sencha.gxt.data.shared.loader.DataProxy;

public class ResourceProxy implements DataProxy<Resource, Resource> {
  @Override
  public void load(Resource resource, final Callback<Resource, Throwable> callback) {

    resource.load(new AsyncCallback<Resource>() {
      @Override
      public void onFailure(Throwable caught) {
        callback.onFailure(caught);
      };

      @Override
      public void onSuccess(Resource result) {
        callback.onSuccess(result);
      }
    });
  }

}
