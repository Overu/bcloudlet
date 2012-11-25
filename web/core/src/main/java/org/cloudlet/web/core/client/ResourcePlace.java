package org.cloudlet.web.core.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.Splittable;

import org.cloudlet.web.core.CoreAutoBeanFactory;
import org.cloudlet.web.core.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class ResourcePlace<T extends Resource> extends Place {

  @Inject
  Provider<ResourceManager> manager;

  @Inject
  Provider<ResourcePlace> placeProvider;

  @Inject
  CoreAutoBeanFactory factory;

  private static final Logger logger = Logger.getLogger(ResourcePlace.class.getName());

  private Class<T> resourceType;

  private Object widget;

  private String path;

  private String title;

  private T resource;

  private String rendition;

  private ResourcePlace<? extends Resource> parent;

  private Map<String, ResourcePlace<? extends Resource>> children;

  private Map<String, ResourcePlace<T>> renditions;

  private MultivaluedMap<String, String> queryParameters;

  //
  // public void addChild(ResourcePlace<? extends Resource> place) {
  // children.put(place.getPath(), place);
  // }

  private ResourcePlace<? extends Resource> host;

  public ResourcePlace<T> copy() {
    ResourcePlace<T> result = placeProvider.get();
    result.setPath(path);
    result.setParent(parent);
    result.setTitle(title);
    result.setResourceType(resourceType);
    return result;
  }

  public void delete(AsyncCallback<ResourcePlace<T>> callback) {
    execute(RequestBuilder.DELETE, callback);
  }

  public void execute(final RequestBuilder.Method method,
      final AsyncCallback<ResourcePlace<T>> callback) {
    try {
      String data = null;
      if (RequestBuilder.POST.equals(method) || RequestBuilder.PUT.equals(method)) {
        data = generateData();
      }
      final StringBuilder url = getUriBuilder();
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
                String data = response.getText();
                JSONObject json = JSONParser.parse(data).isObject();
                String typeName = json.get("type").isString().stringValue();
                Class<T> resourceType = (Class<T>) ClientPlatform.getResourceType(typeName);
                if (resourceType == null) {
                  resourceType = getResourceType();
                }
                if (resourceType == null) {
                  resourceType = (Class<T>) Resource.class;
                }
                setResourceType(resourceType);
                AutoBean<T> result = AutoBeanCodex.decode(factory, resourceType, data);
                final T resource = result.as();
                setResource(resource);
              }
            }
            if (callback != null) {
              callback.onSuccess(ResourcePlace.this);
            }
          } else if (callback != null) {
            callback.onFailure(new RuntimeException("GET " + url.toString()
                + "\r\nInvalid status code " + statusCode));
          }
        }
      });
    } catch (Exception e) {
      callback.onFailure(e);
    }
  }

  public void get(AsyncCallback<ResourcePlace<T>> callback) {
    resolve(resourceType, callback);
  }

  public ResourcePlace getByUri(String uri) {
    String[] parts = uri.split("\\?");
    String[] segments = parts[0].split("/");
    ResourcePlace result = this;
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

      String renditionKind = paramMap.getFirst(Resource.RENDITION);
      if (renditionKind != null) {
        result = result.getRendition(renditionKind);
        paramMap.remove(Resource.RENDITION);
      }
      result.setQueryParameters(paramMap);
    }
    return result;
  }

  public ResourcePlace getChild(String path) {
    return getChild(path, true);
  }

  public ResourcePlace getChild(String path, boolean create) {
    ResourcePlace result = null;
    if (children != null) {
      result = children.get(path);
    }
    if (result == null) {
      if (create) {
        result = placeProvider.get();
        result.setPath(path);
        result.setParent(this);
        getChildren().put(path, result);
      } else {
        return null;
      }
    }
    return result;
  }

  public Map<String, ResourcePlace<? extends Resource>> getChildren() {
    if (children == null) {
      children = new HashMap<String, ResourcePlace<? extends Resource>>();
    }
    return children;
  }

  public ResourcePlace getHost() {
    if (rendition == null) {
      return this;
    } else {
      return host;
    }
  }

  public ResourcePlace getParent() {
    return parent;
  }

  public String getPath() {
    return path;
  }

  public MultivaluedMap<String, String> getQueryParameters() {
    if (queryParameters == null) {
      queryParameters = new MultivaluedHashMap<String, String>();
    }
    return queryParameters;
  }

  public String getRendition() {
    return rendition;
  }

  public ResourcePlace<T> getRendition(String kind) {
    ResourcePlace<T> result = getRenditions().get(kind);
    if (result == null) {
      result = copy();
      result.setRendition(kind);
      result.setTitle(kind);
      result.host = this;
      if (Resource.SELF.equals(kind)) {
        result.setResource(this.resource);
      }
      getRenditions().put(kind, result);
    }
    return result;
  }

  public Map<String, ResourcePlace<T>> getRenditions() {
    if (renditions == null) {
      renditions = new HashMap<String, ResourcePlace<T>>();
    }
    return renditions;
  }

  public T getResource() {
    return resource;
  }

  public Class<T> getResourceType() {
    return resourceType;
  }

  public String getTitle() {
    if (resource == null || (rendition != null && rendition.length() > 0)) {
      return title;
    }
    return resource.getTitle();
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
      if (builder.length() > 1) {
        builder.append("/");
      }
      builder.append(path);
    }

    if (includeParams) {
      boolean first = true;
      if (rendition != null && rendition.length() > 0) {
        first = appendParam(builder, Resource.RENDITION, rendition, first);
      }
      for (String key : getQueryParameters().keySet()) {
        List<String> values = getQueryParameters().get(key);
        for (String value : values) {
          first = appendParam(builder, key, value, first);
        }
      }
    }
    return builder;
  }

  public Object getWidget() {
    return widget;
  }

  public boolean hasChildren() {
    if (rendition != null && rendition.length() > 0) {
      return false;
    }
    if (resource != null) {
      return resource.getChildrenCount() > 0;
    }
    return true;
  }

  public void load(AsyncCallback<ResourcePlace<T>> callback) {
    execute(RequestBuilder.GET, callback);
  }

  public void post(AsyncCallback<ResourcePlace<T>> callback) {
    execute(RequestBuilder.POST, callback);
  }

  public void put(AsyncCallback<ResourcePlace<T>> callback) {
    execute(RequestBuilder.PUT, callback);
  }

  public void render(final AcceptsOneWidget panel) {
    renderAll(new ResourceContainer() {
      @Override
      public void addResourceWidget(IsWidget widget) {
        panel.setWidget(widget);
      }

    }, null);
  }

  public void renderAll(final ResourceContainer container, final AsyncCallback<IsWidget> callback) {
    if (parent != null) {
      parent.renderAll(container, new AsyncCallback<IsWidget>() {
        @Override
        public void onFailure(final Throwable caught) {
          logger.info("Failured to load parent widget. Show " + ResourcePlace.this + " directly.");
        }

        @Override
        public void onSuccess(final IsWidget result) {
          if (result instanceof ResourceContainer) {
            renderCurrent((ResourceContainer) result, callback);
          } else {
            logger.info(result.getClass().getName()
                + " must implement ResourceContainer to render child widget.");
          }
        }
      });
    } else {
      renderCurrent(container, callback);
    }
  }

  public void renderCurrent(final ResourceContainer container,
      final AsyncCallback<IsWidget> callback) {
    if (widget == null) {
      if (resource == null) {
        load(new AsyncCallback<ResourcePlace<T>>() {
          @Override
          public void onFailure(Throwable caught) {
          }

          @Override
          public void onSuccess(ResourcePlace<T> result) {
            renderCurrent(container, callback);
          }
        });
        return;
      } else {
        String type = resource.getType();
        widget = ClientPlatform.getWidget(type, rendition);
        if (widget == null) {
          if (callback != null) {
            // skip to render child resource
            if (container instanceof IsWidget) {
              callback.onSuccess((IsWidget) container);
            }
          }
          return;
        }
        logger.info(type);
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

  public void resolve(final Class<T> resourceType, final AsyncCallback<ResourcePlace<T>> callback) {
    if (resource != null && (resourceType.equals(this.resourceType))) {
      if (callback != null) {
        callback.onSuccess(this);
      }
      return;
    }
    setResourceType(resourceType);
    if (resource != null) {
      // Re-decode according to given resourceType
      AutoBean<T> bean = AutoBeanUtils.getAutoBean(resource);
      Splittable s = AutoBeanCodex.encode(bean);
      bean = AutoBeanCodex.decode(factory, resourceType, s);
      resource = bean.as();
      return;
    } else {
      load(callback);
    }
  }

  public void setParent(ResourcePlace parent) {
    this.parent = parent;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setQueryParameters(MultivaluedMap<String, String> queryParameters) {
    this.queryParameters = queryParameters;
  }

  public void setRendition(String rendition) {
    this.rendition = rendition;
  }

  public void setResource(T resource) {
    this.resource = resource;
  }

  public void setResourceType(Class<T> resourceType) {
    this.resourceType = resourceType;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setWidget(Object widget) {
    this.widget = widget;
  }

  @Override
  public String toString() {
    StringBuilder builder = getUriBuilder();
    if (resourceType != null) {
      builder.append(" [").append(resourceType).append("]");
    }
    return builder.toString();
  }

  protected String generateData() {
    AutoBean<? extends Resource> bean = AutoBeanUtils.getAutoBean(resource);
    Splittable data = AutoBeanCodex.encode(bean);
    String payload = data.getPayload();
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

  private void appendWidget(IsWidget widget, final ResourceContainer panel,
      final AsyncCallback<IsWidget> callback) {
    setWidget(widget);
    panel.addResourceWidget(widget);
    if (widget instanceof ResourceWidget) {
      final ResourceWidget<T> rw = (ResourceWidget<T>) widget;
      Class<T> rt = rw.getResourceType();
      resolve(rt, new AsyncCallback<ResourcePlace<T>>() {
        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess(ResourcePlace<T> result) {
          rw.setPlace(ResourcePlace.this);
        }
      });
    }
    if (callback != null) {
      callback.onSuccess(widget);
    }
  }
}
