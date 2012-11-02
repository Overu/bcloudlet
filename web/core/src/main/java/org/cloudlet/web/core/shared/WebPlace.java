package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.client.ViewType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class WebPlace extends Place {

  @Inject
  Provider<WebPlace> placeProvider;

  @Inject
  Provider<WebPlaceManager> placeManager;

  private String path;

  private String title;

  private WebPlace parent;

  private List<WebPlace> children = new ArrayList<WebPlace>();

  private AcceptsOneWidget container;

  private Map<String, IsWidget> viewers;

  private String buttonText;

  private PlaceType placeType;

  private static final Logger logger = Logger.getLogger(WebPlace.class.getName());

  private String viewType = ViewType.HOME_PAGE;

  public void addChild(final WebPlace place) {
    children.add(place);
    place.parent = this;
  }

  public WebPlace findChild(final String uri) {
    String[] pair = uri.split("#");
    String viewType = pair.length > 1 ? pair[1] : "";
    String[] segments = pair[0].split("/");
    WebPlace result = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      result = result.getChild(path);
    }
    result.viewType = viewType;
    return result;
  }

  public String getButtonText() {
    return buttonText;
  }

  public WebPlace getChild(final int index) {
    return children.get(index);
  }

  public WebPlace getChild(final String path) {
    for (WebPlace p : children) {
      if (path.equals(p.getPath())) {
        return p;
      }
    }
    return null;
  }

  public List<WebPlace> getChildren() {
    return children;
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
    if (title == null) {
      return getPath();
    }
    return title;
  }

  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    if (parent == null) {
      return new StringBuilder();
    } else {
      StringBuilder builder = parent.getUriBuilder();
      if (path != null) {
        builder.append("/").append(path);
      }
      return builder;
    }
  }

  public String getViewType() {
    return viewType;
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

  public void render(final AcceptsOneWidget panel) {
    renderParents(panel, new AsyncCallback<AcceptsOneWidget>() {
      @Override
      public void onFailure(Throwable caught) {
      }

      @Override
      public void onSuccess(AcceptsOneWidget result) {
        IsWidget widget = getViewers().get(viewType);
        if (widget != null) {
          result.setWidget(widget);
        } else {
          placeType.renderView(result, viewType, new AsyncCallback<IsWidget>() {
            @Override
            public void onFailure(Throwable caught) {
              logger.severe("No view found for " + getUri() + "#" + viewType);
            }

            @Override
            public void onSuccess(IsWidget result) {
              getViewers().put(viewType, result);
            }
          });
        }
      }
    });
  }

  public void setButtonText(final String buttonText) {
    this.buttonText = buttonText;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public void setPlaceType(PlaceType placeType) {
    this.placeType = placeType;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public void setViewType(String viewType) {
    this.viewType = viewType;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getTitle()).append(" (").append(getUri()).append(")");
    return builder.toString();
  }

  private Map<String, IsWidget> getViewers() {
    if (viewers == null) {
      viewers = new HashMap<String, IsWidget>();
    }
    return viewers;
  }

  private void renderContainer(final AcceptsOneWidget parentContainer,
      final AsyncCallback<AcceptsOneWidget> childContainer) {
    if (container != null) {
      parentContainer.setWidget((IsWidget) container);
      if (childContainer != null) {
        childContainer.onSuccess(container);
      }
    } else if (placeType != null) {
      boolean hasContainerView =
          placeType.renderView(parentContainer, ViewType.CONTAINER, new AsyncCallback<IsWidget>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(IsWidget result) {
              if (result instanceof AcceptsOneWidget) {
                container = (AcceptsOneWidget) result;
                if (childContainer != null) {
                  childContainer.onSuccess(container);
                }
              } else {
                logger.severe("Parent widget does not implement AcceptsOneWidget. Can not render "
                    + this);
              }
            }
          });
      if (!hasContainerView) { // No container for this place skip to child node
        childContainer.onSuccess(parentContainer);
      }
    }
  };

  private void renderParents(final AcceptsOneWidget panel,
      final AsyncCallback<AcceptsOneWidget> callback) {
    if (parent != null) {
      parent.renderParents(panel, new AsyncCallback<AcceptsOneWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          logger.info("Failured to load parent widget. Show " + this + " directly.");
          renderContainer(panel, callback);
        }

        @Override
        public void onSuccess(final AcceptsOneWidget result) {
          renderContainer((AcceptsOneWidget) result, callback);
        }
      });
    } else {
      renderContainer(panel, callback);
    }
  }

}
