package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.sencha.gxt.data.shared.loader.DataProxy;

import org.cloudlet.web.core.shared.ResourcePlace;

public class ResourceProxy<T extends ResourcePlace> implements DataProxy<T, T> {

  @Override
  public void load(final T place, final Callback<T, Throwable> callback) {
    place.load(new AsyncCallback<T>() {
      @Override
      public void onFailure(Throwable caught) {
        callback.onFailure(caught);
      };

      @Override
      public void onSuccess(T result) {
        callback.onSuccess(result);
      }
    });
  }

}
