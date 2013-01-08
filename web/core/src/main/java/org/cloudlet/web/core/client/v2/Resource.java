package org.cloudlet.web.core.client.v2;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class Resource extends Place {

  private static final Logger logger = Logger.getLogger(Resource.class.getName());

  public static final String ID = "id";

  public static final String TITLE = "title";

  public static final String PATH = "path";

  public static final String URI = "uri";

  public static final String VERSION = "version";

  public static final String CHILDREN = "children";

  public static final String CHILDREN_COUNT = "childrenCount";

  public static final String CONTAINER = "/";

  public static final String SELF = "_";

  public static final String RESOURCE_TYPE = "resourceType";

  public static final String PARENT_TYPE = "parentType";

  public static final String PARENT_ID = "parentId";

  public static final String QUERY_COUNT = "queryCount";

  @Inject
  Provider<Resource> placeProvider;

  @Inject
  private JSONObject data;

  private Object widget;

  private Resource parent;

  private Map<String, Resource> children;

  private List<Resource> entries;

  private MultivaluedMap<String, String> queryParameters;

  //
  // public void addChild(ResourcePlace place) {
  // children.put(place.getPath(), place);
  // }

  private Map<String, Resource> renditions;

  private Object containerWidget;

  public Resource createChild(String path) {
    Resource result = placeProvider.get();
    result.setPath(path);
    result.setParent(this);
    getChildren().put(path, result);
    return result;
  }

  public void delete(AsyncCallback<Resource> callback) {
    execute(RequestBuilder.DELETE, callback);
  }

  public void execute(final RequestBuilder.Method method, final AsyncCallback<Resource> callback) {
    try {
      String data = null;
      if (RequestBuilder.POST.equals(method) || RequestBuilder.PUT.equals(method)) {
        data = generateData();
      }
      final StringBuilder url = RequestBuilder.POST.equals(method) ? getParent().getUriBuilder() : getUriBuilder();
      url.insert(0, "api");
      RequestBuilder builder = new RequestBuilder(method, url.toString());
      builder.setHeader("Accept", "application/json");
      if (RequestBuilder.POST.equals(method) || RequestBuilder.PUT.equals(method)) {
        builder.setHeader("Content-Type", "application/json");
      }
      builder.sendRequest(data, new RequestCallback() {

        @Override
        public void onError(Request request, Throwable exception) {
          if (callback != null) {
            callback.onFailure(exception);
          }
        }

        @Override
        public void onResponseReceived(Request request, Response response) {
          int statusCode = response.getStatusCode();
          if (statusCode / 100 == 2) {
            if (statusCode == Response.SC_OK) {
              if (!method.equals(RequestBuilder.DELETE)) {
                read(response.getText());
              }
            }
            if (callback != null) {
              callback.onSuccess(Resource.this);
            }
          } else if (callback != null) {
            callback.onFailure(new RuntimeException("GET " + url.toString() + "\r\nInvalid status code " + statusCode));
          }
        }

      });
    } catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void get(AsyncCallback<Resource> callback) {
    if (data != null) {
      if (callback != null) {
        callback.onSuccess(this);
      }
      return;
    } else {
      load(callback);
    }
  }

  public Resource getByUri(String uri) {
    String[] parts = uri.split("\\?");
    String[] segments = parts[0].split("/");
    Resource result = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      result = result.getChild(path);
    }
    if (parts.length > 1) {
      String queryString = parts[1];
      String[] params = queryString.split("&");
      MultivaluedMap<String, String> paramMap = new MultivaluedHashMap<String, String>();
      for (String param : params) {
        int index = param.indexOf("=");
        String paramName = index >= 0 ? param.substring(0, index) : param;
        String paramValue = index >= 0 ? param.substring(index + 1) : "";
        paramMap.add(paramName, paramValue);
      }
      result.setQueryParameters(paramMap);
    }
    return result;
  }

  public Resource getChild(String path) {
    return getChild(path, true);
  }

  public Resource getChild(String path, boolean create) {
    getRenditions();// ensure renditions are initialized;
    Resource result = getChildren().get(path);
    if (result == null && create) {
      result = createChild(path);
    }
    return result;
  }

  public Map<String, Resource> getChildren() {
    if (children == null) {
      children = new HashMap<String, Resource>();
    }
    return children;
  }

  public Object getContainerWidget() {
    return containerWidget;
  }

  public List<Resource> getEntries() {
    if (entries == null) {
      entries = new ArrayList<Resource>();
      JSONValue value = data.get("children");
      if (value != null && value.isArray() != null) {
        JSONArray array = value.isArray();
        for (int i = 0; i < array.size(); i++) {
          JSONValue v = array.get(i);
          JSONObject obj = v.isObject();
          String path = obj.get(PATH).isString().stringValue();
          Resource child = getChild(path);
          child.data = obj;
          entries.add(child);
        }
      }
    }
    return entries;
  }

  public String getId() {
    return getString(ID);
  }

  public List<Resource> getList(String prop) {
    List<Resource> result = new ArrayList<Resource>();
    JSONValue value = data.get(prop);
    if (value != null && value.isArray() != null) {
      JSONArray array = value.isArray();
      for (int i = 0; i < array.size(); i++) {
        JSONValue v = array.get(i);
        JSONObject obj = v.isObject();
        String path = obj.get(PATH).isString().stringValue();
        Resource child = getChild(path);
        child.data = obj;
        result.add(child);
      }
    }
    return result;
  }

  public Resource getParent() {
    return parent;
  }

  public String getPath() {
    return getString(PATH);
  }

  public Long getQueryCount() {
    Object result = getValue(QUERY_COUNT);
    if (result != null && result instanceof Double) {
      return ((Double) result).longValue();
    }
    return null;
  }

  public MultivaluedMap<String, String> getQueryParameters() {
    if (queryParameters == null) {
      queryParameters = new MultivaluedHashMap<String, String>();
    }
    return queryParameters;
  }

  public Resource getRendition(String kind) {
    if (isRendition()) {
      return getParent().getRenditions().get(kind);
    } else {
      return getRenditions().get(kind);
    }
  }

  public Map<String, Resource> getRenditions() {
    if (isRendition()) {
      return Collections.EMPTY_MAP;
    }
    if (renditions == null) {
      renditions = new HashMap<String, Resource>();
      String resourceType = getResourceType();
      if (resourceType != null) {
        Map<String, Object> widgets = Registry.getWidgets(resourceType);
        if (!widgets.containsKey(SELF)) {
          widgets.put(SELF, new SimplePanel());
        }
        for (String kind : widgets.keySet()) {
          if (CONTAINER.equals(kind)) {
            continue;
          }
          Object widget = widgets.get(kind);
          Resource rendition = getChild(kind);
          rendition.setTitle(kind);// TODO localize rendition title
          rendition.setWidget(widget);
          renditions.put(kind, rendition);
        }
      }
    }
    return renditions;
  }

  public String getResourceType() {
    return getString(RESOURCE_TYPE);
  }

  public String getString(String propName) {
    JSONValue value = data.get(propName);
    if (value != null) {
      JSONString str = value.isString();
      return str != null ? str.stringValue() : null;
    }
    return null;
  }

  public String getTitle() {
    return getString(TITLE);
  }

  public String getUri() {
    return getUriBuilder().toString();
  }

  public StringBuilder getUriBuilder() {
    return getUriBuilder(true);
  }

  public StringBuilder getUriBuilder(boolean includeParams) {
    StringBuilder builder;
    if (getParent() == null) {
      builder = new StringBuilder("/");
    } else {
      builder = getParent().getUriBuilder(false);
      String path = getPath();
      if (path != null) {
        builder.append(path);
        if (!isRendition()) {
          builder.append("/");
        }
      }
    }

    if (includeParams) {
      boolean first = true;
      for (String key : getQueryParameters().keySet()) {
        List<String> values = getQueryParameters().get(key);
        for (String value : values) {
          first = appendParam(builder, key, value, first);
        }
      }
    }
    return builder;
  }

  public Object getValue(String propName) {
    JSONValue value = data.get(propName);
    if (value instanceof JSONString) {
      return value.isString().stringValue();
    } else if (value instanceof JSONBoolean) {
      return value.isBoolean().booleanValue();
    } else if (value instanceof JSONNumber) {
      return value.isNumber().doubleValue();
    } else {
      return null;
    }
  }

  public Object getWidget() {
    return widget;
  }

  public boolean hasChildren() {
    if (isRendition()) {
      return false;
    }
    JSONValue count = data.get(CHILDREN_COUNT);
    if (count != null) {
      return count.isNumber().doubleValue() > 0;
    }
    return true;
  }

  public boolean isRendition() {
    String path = getPath();
    return path != null && path.startsWith(SELF);
  }

  public void load(AsyncCallback<Resource> callback) {
    execute(RequestBuilder.GET, callback);
  }

  public void post(AsyncCallback<Resource> callback) {
    execute(RequestBuilder.POST, callback);
  }

  public void put(AsyncCallback<Resource> callback) {
    execute(RequestBuilder.PUT, callback);
  }

  public void read(String jsonText) {
    // TODO clear data
    data = JSONParser.parse(jsonText).isObject();
    entries = null;
  }

  public void render(final AcceptsOneWidget panel) {
    renderFromRoot(new WidgetContainer() {
      @Override
      public void acceptWidget(IsWidget widget) {
        panel.setWidget(widget);
      }
    }, null);
  }

  public void renderCurrent(final AcceptsOneWidget panel) {
    renderCurrent(new WidgetContainer() {
      @Override
      public void acceptWidget(IsWidget widget) {
        panel.setWidget(widget);
      }
    }, null);
  }

  public void renderCurrent(final WidgetContainer container, final AsyncCallback<IsWidget> callback) {
    if (widget == null) {
      String resourceType = getResourceType();
      if (resourceType == null) {
        load(new AsyncCallback<Resource>() {
          @Override
          public void onFailure(Throwable caught) {
          }

          @Override
          public void onSuccess(Resource result) {
            renderCurrent(container, callback);
          }
        });
        return;
      } else {
        String kind = isRendition() ? getPath() : CONTAINER;
        widget = Registry.getWidget(resourceType, kind);
        if (widget == null) {
          if (callback != null) {
            // skip to render child resource
            if (container instanceof IsWidget) {
              callback.onSuccess((IsWidget) container);
            }
          }
          return;
        }
        logger.info(resourceType);
      }
    }
    if (widget instanceof IsWidget) {
      appendWidget((IsWidget) widget, container, callback);
    } else if (widget instanceof Provider) {
      appendWidget(((Provider<IsWidget>) widget).get(), container, callback);
    } else if (widget instanceof AsyncProvider) {
      AsyncProvider<IsWidget> provider = (AsyncProvider<IsWidget>) widget;
      provider.get(new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          if (callback != null) {
            callback.onFailure(caught);
          }
        }

        @Override
        public void onSuccess(final IsWidget result) {
          appendWidget(result, container, callback);
        }
      });
    }
  }

  public void renderFromRoot(final WidgetContainer container, final AsyncCallback<IsWidget> callback) {
    if (parent != null) {
      parent.renderFromRoot(container, new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          logger.info("Failured to load parent widget. Show " + Resource.this + " directly.");
        }

        @Override
        public void onSuccess(final IsWidget result) {
          if (result instanceof WidgetContainer) {
            renderCurrent((WidgetContainer) result, callback);
          } else {
            logger.info(result.getClass().getName() + " must implement ResourceContainer to render child widget.");
          }
        }
      });
    } else {
      renderCurrent(container, callback);
    }
  }

  public void save(AsyncCallback<Resource> callback) {
    boolean isEdit = data.get(ID).isString() != null;
    RequestBuilder.Method method = isEdit ? RequestBuilder.POST : RequestBuilder.PUT;
    execute(method, callback);
  }

  public void setContainerWidget(Object containerWidget) {
    this.containerWidget = containerWidget;
  }

  public void setParent(Resource parent) {
    this.parent = parent;
  }

  public void setPath(String path) {
    setString(PATH, path);
  }

  public void setQueryParameters(MultivaluedMap<String, String> queryParameters) {
    this.queryParameters = queryParameters;
  }

  public void setResourceType(String value) {
    setString(RESOURCE_TYPE, value);
  }

  public void setString(String propName, String value) {
    data.put(propName, new JSONString(value));
  }

  public void setTitle(String title) {
    setString(TITLE, title);
  }

  public void setValue(String propName, Boolean value) {
    data.put(propName, JSONBoolean.getInstance(value));
  }

  public void setValue(String propName, Number value) {
    data.put(propName, new JSONNumber(value.doubleValue()));
  }

  public void setValue(String propName, Object value) {
    if (value == null) {
      data.put(propName, JSONNull.getInstance());
    } else if (value instanceof Number) {
      setValue(propName, (Number) value);
    } else if (value instanceof Boolean) {
      setValue(propName, (Boolean) value);
    } else {
      setString(propName, value.toString());
    }
  }

  public void setWidget(Object widget) {
    this.widget = widget;
  }

  @Override
  public String toString() {
    StringBuilder builder = getUriBuilder();
    String resourceType = getResourceType();
    if (resourceType != null) {
      builder.append(" [").append(resourceType).append("]");
    }
    return builder.toString();
  }

  protected String generateData() {
    String payload = data.toString();
    return payload;
  }

  private boolean appendParam(StringBuilder builder, String key, String value, boolean first) {
    if (first) {
      builder.append("?");
      first = false;
    } else {
      builder.append("&");
    }
    builder.append(key).append("=").append(value);
    return first;
  }

  private void appendWidget(IsWidget widget, final WidgetContainer panel, final AsyncCallback<IsWidget> callback) {
    setWidget(widget);
    panel.acceptWidget(widget);
    if (widget instanceof TakesResource) {
      final TakesResource takesRes = (TakesResource) widget;
      get(new AsyncCallback<Resource>() {
        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess(Resource result) {
          takesRes.setValue(Resource.this);
        }
      });
    }
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }
}
