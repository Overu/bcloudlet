package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WebPlace extends Place {

  public static final String HOME = "";

  public static final String FOLDER = "/";

  public static final String POST = "action=create";

  public static boolean isFolder(final String uri) {
    return uri == null || (uri.length() > 0 && !uri.contains("="));
  }

  @Inject
  Provider<WebPlace> placeProvider;

  @Inject
  Provider<WebPlaceManager> placeManager;

  private String path;

  private WebPlace parent;

  private List<WebPlace> children = new ArrayList<WebPlace>();

  private IsWidget widget;

  private String title;

  private PlaceType placeType;

  private static final Logger logger = Logger.getLogger(WebPlace.class.getName());

  private boolean viewer;

  public void addChild(final WebPlace place) {
    children.add(place);
    place.parent = this;
  }

  public WebPlace findChild(final String uri) {
    String[] segments = uri.split("/|\\?");
    WebPlace result = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      result = result.getChild(path);
    }
    return result;
  }

  public WebPlace getChild(final String path) {
    for (WebPlace p : children) {
      if (path.equals(p.getPath())) {
        return p;
      }
    }
    if (isFolder(path)) {
      PlaceType t = placeType.getTargetType(path);
      if (t != null) {
        WebPlace place = placeProvider.get();
        place.setPath(path);
        place.setPlaceType(t);
        addChild(place);
        return place;
      }
    } else if (placeType.getWidget(path) != null) {
      WebPlace place = placeProvider.get();
      place.setPath(path);
      place.setPlaceType(placeType);
      addChild(place);
      return place;
    }
    return null;
  }

  public WebPlace getHome() {
    return getChild(WebPlace.HOME);
  }

  public WebPlace getParent() {
    return parent;
  }

  public String getPath() {
    return path;
  }

  public PlaceType getPlaceType() {
    return placeType;
  }

  public String getTitle() {
    return title;
  }

  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    if (parent == null) {
      return new StringBuilder("/");
    } else {
      StringBuilder builder = parent.getUriBuilder();
      if (path.length() > 0) {
        if (isFolder()) {
          builder.append(path);
          builder.append("/");
        } else {
          builder.append("?");
          builder.append(path);
        }
      }
      return builder;
    }
  }

  public String getWidgetId() {
    if (isFolder()) {
      return WebPlace.FOLDER;
    } else {
      return path;
    }
  }

  public boolean isChildOf(final WebPlace place) {
    if (place == this) {
      return true;
    } else if (parent != null) {
      return parent.isChildOf(place);
    } else {
      return false;
    }
  }

  public boolean isFolder() {
    return isFolder(path);
  }

  public boolean isViewer() {
    return viewer;
  }

  public void render(final AcceptsOneWidget panel) {
    render(panel, null);
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public void setPlaceType(final PlaceType placeType) {
    this.placeType = placeType;
  };

  public void setTitle(final String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(placeType.getName()).append(" (").append(getUri()).append(")");
    return builder.toString();
  }

  private void appendWidget(final AcceptsOneWidget panel, final AsyncCallback<IsWidget> callback) {
    panel.setWidget(widget);
    if (widget instanceof WebView) {
      ((WebView) widget).setPlace(WebPlace.this);
    }
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }

  private void render(final AcceptsOneWidget panel, final AsyncCallback<IsWidget> callback) {
    if (parent != null) {
      parent.render(panel, new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          logger.info("Failured to load parent widget. Show " + this + " directly.");
          renderWidget(panel, callback);
        }

        @Override
        public void onSuccess(final IsWidget result) {
          if (result instanceof AcceptsOneWidget) {
            renderWidget((AcceptsOneWidget) result, callback);
          } else {
            logger.info(result.getClass().getName()
                + " must implement AcceptsOneWidget to render child widget.");
          }
        }
      });
    } else {
      renderWidget(panel, callback);
    }
  }

  private void renderWidget(final AcceptsOneWidget panel, final AsyncCallback<IsWidget> callback) {
    if (widget != null) {
      appendWidget(panel, callback);
    } else if (placeType != null) {
      boolean hasWidget = placeType.loadWidget(getWidgetId(), new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
        }

        @Override
        public void onSuccess(final IsWidget result) {
          widget = result;
          appendWidget(panel, callback);
        }

      });
      if (!hasWidget && callback != null) {
        // No container for this place skip to child node
        if (panel instanceof IsWidget) {
          callback.onSuccess((IsWidget) panel);
        }
      }
    }
  }
}
