package org.cloudlet.web.core.shared;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

import org.cloudlet.web.boot.shared.MapBinder;

import java.util.logging.Logger;

public class PlaceType extends MapBinder<String, IsWidget> {

  public static PlaceType ROOT = new PlaceType();

  private static final Logger logger = Logger.getLogger(PlaceType.class.getName());

  private PlaceType parent;

  public PlaceType() {
  }

  public PlaceType(PlaceType parent, String name) {
    this.parent = parent;
  }

  public boolean renderView(final AcceptsOneWidget panel, final String viewType,
      final AsyncCallback<IsWidget> callback) {
    IsWidget widget = get(viewType);
    if (widget == null) {
      Provider<IsWidget> provider = getProvider(viewType);
      if (provider != null) {
        widget = provider.get();
      }
    }

    if (widget == null) {
      AsyncProvider<IsWidget> provider = getAsyncProvider(viewType);
      if (provider != null) {
        provider.get(new AsyncCallback<IsWidget>() {
          @Override
          public void onFailure(final Throwable caught) {
            String msg = "Failed to load view by id=" + viewType;
            logger.info(msg);
            if (callback != null) {
              callback.onFailure(caught);
            }
          }

          @Override
          public void onSuccess(final IsWidget result) {
            appendWidget(panel, result, callback);
          }
        });
        return true;
      } else {
        return false;
      }
    } else {
      appendWidget(panel, widget, callback);
      return true;
    }
  }

  private void appendWidget(final AcceptsOneWidget panel, final IsWidget widget,
      final AsyncCallback<IsWidget> callback) {
    panel.setWidget(widget);
    if (callback != null && widget instanceof AcceptsOneWidget) {
      callback.onSuccess(widget);
    }
  }
}
