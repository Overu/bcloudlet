package org.cloudlet.web.core.shared;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

import org.cloudlet.web.boot.shared.MapBinder;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlaceType extends MapBinder<String, IsWidget> {

  private static final Logger logger = Logger.getLogger(PlaceType.class.getName());

  private PlaceType parent;

  public static Map<String, PlaceType> types = new HashMap<String, PlaceType>();

  public static PlaceType getType(String name) {
    return types.get(name);
  }

  private String name;

  public PlaceType() {
  }

  public PlaceType(PlaceType parent, String name) {
    this.parent = parent;
    this.name = name;
    types.put(name, parent);
  }

  public PlaceType(String name) {
    this(null, name);
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
      } else if (parent != null) {
        return parent.renderView(panel, viewType, callback);
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
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }
}
