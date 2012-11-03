package org.cloudlet.web.core.shared;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlaceType {

  private static final Logger logger = Logger.getLogger(PlaceType.class.getName());

  private PlaceType parent;

  public static Map<String, PlaceType> types = new HashMap<String, PlaceType>();

  public static Map<String, PlaceType> references = new HashMap<String, PlaceType>();

  public static Map<String, Object> viewers = new HashMap<String, Object>();

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

  public void addReference(String path, PlaceType type) {
    references.put(path, type);
  }

  public String getName() {
    return name;
  }

  public PlaceType getReference(String path) {
    return references.get(path);
  }

  public Object getWidget(String name) {
    return viewers.get(name);
  }

  public boolean loadWidget(final String widgetId, final AsyncCallback<IsWidget> callback) {
    Object widget = getWidget(widgetId);
    if (widget == null) {
      if (parent != null) {
        return parent.loadWidget(widgetId, callback);
      } else {
        return false;
      }
    }
    if (widget instanceof IsWidget) {
      callback.onSuccess((IsWidget) widget);
    } else if (widget instanceof Provider) {
      callback.onSuccess(((Provider<IsWidget>) widget).get());
    } else {
      AsyncProvider<IsWidget> provider = (AsyncProvider<IsWidget>) widget;
      provider.get(new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          callback.onFailure(caught);
        }

        @Override
        public void onSuccess(final IsWidget result) {
          callback.onSuccess(result);
        }
      });
    }
    return true;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWidget(String name, AsyncProvider<IsWidget> asyncProvider) {
    viewers.put(name, asyncProvider);
  }

  public void setWidget(String name, IsWidget widget) {
    viewers.put(name, widget);
  }

  public void setWidget(String name, Provider<IsWidget> provider) {
    viewers.put(name, provider);
  }

}
