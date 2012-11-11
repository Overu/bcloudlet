package org.cloudlet.web.core.shared;

// import com.google.gwt.json.client.JSONObject;
// import com.google.gwt.json.client.JSONValue;
import com.google.gwt.core.shared.GWT;

import org.cloudlet.web.core.server.ResourceEntity;
import org.hibernate.annotations.TypeDef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@TypeDef(name = "content", typeClass = ResourceEntity.class)
@MappedSuperclass
@XmlType
public abstract class Content extends Resource {

  private static final Logger logger = Logger.getLogger(Content.class.getName());

  public static ContentType TYPE = new ContentType(Resource.TYPE, "content");

  public static final String CHILDREN_COUNT = "childrenCount";

  protected long childrenCount;

  @Transient
  private Map<String, Resource> cache;

  @Transient
  private Map<String, View> allViews;

  @Transient
  private Map<String, View> localViews;

  @Transient
  private List<View> remoteViews;

  public Resource findChild(final String uriWithQueryString) {
    int index = uriWithQueryString.indexOf("?");
    String uri = index > 0 ? uriWithQueryString.substring(0, index) : uriWithQueryString;
    String[] segments = uri.split("/");
    String queryStr = index >= 0 ? uriWithQueryString.substring(index) : null;

    Resource result = null;
    Content parent = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      Content child = null;
      if (parent instanceof Entry) {
        child = ((Entry) parent).getRelationship(path);
      } else if (parent instanceof Feed) {
        child = ((Feed) parent).getEntry(path);
      }
      if (child != null) {
        parent = child;
      } else if (GWT.isClient()) {
        result = new ResourceProxy();
        result.setParent(parent);
      }
    }
    if (result != null) {
      result.setPath(uriWithQueryString.substring(parent.getUri().length()));
    } else if (parent != null) {
      if (queryStr != null) {
        result = parent.getView(queryStr);
      } else {
        result = parent;
      }
    }
    return result;
  }

  @XmlTransient
  public Map<String, View> getAllViews() {
    if (allViews == null) {
      allViews = new HashMap<String, View>();
      allViews.putAll(getLocalViews());
      if (remoteViews != null) {
        for (View v : remoteViews) {
          allViews.put(v.getWidgetKey(), v);
        }
      }
    }
    return allViews;
  }

  public Map<String, Resource> getCache() {
    if (cache == null) {
      cache = new HashMap<String, Resource>();
    }
    return cache;
  }

  public long getChildrenCount() {
    return childrenCount;
  }

  @XmlTransient
  public View getHomeView() {
    return getView(View.HOME);
  }

  @XmlTransient
  public Map<String, View> getLocalViews() {
    if (localViews == null) {
      localViews = new HashMap<String, View>();
      for (String key : getResourceType().getWidgetKeys()) {
        Object widget = getResourceType().getWidget(key);
        if (widget != null) {
          View view = new View();
          view.setParent(this);
          view.setWidgetKey(key);
          localViews.put(key, view);
        }
      }
    }
    return localViews;
  }

  @XmlTransient
  public List<View> getRemoteViews() {
    return remoteViews;
  }

  @Override
  public ContentType<?> getResourceType() {
    return TYPE;
  }

  public View getView(String widgetId) {
    return getAllViews().get(widgetId);
  }

  @Override
  public Object getWidget() {
    if (widget == null) {
      widget = getResourceType().getWidget(getWidgetKey());
    }
    return widget;
  }

  @Override
  public String getWidgetKey() {
    return View.CONTENT;
  }

  public void setChildrenCount(long totalResults) {
    this.childrenCount = totalResults;
  }

  @Override
  public void setProperty(String name, String value) {
    if (CHILDREN_COUNT.equals(name)) {
      childrenCount = value == null ? 0 : Long.valueOf(value);
    } else {
      super.setProperty(name, value);
    }
  }

  public void setRemoteViews(List<View> remoteViews) {
    this.remoteViews = remoteViews;
  }
}
