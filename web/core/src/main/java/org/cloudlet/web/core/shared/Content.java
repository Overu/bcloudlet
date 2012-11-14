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
@XmlType(name = Content.TYPE_NAME)
public abstract class Content extends Resource {

  @SuppressWarnings("hiding")
  public static final String TYPE_NAME = "Content";

  private static final Logger logger = Logger.getLogger(Content.class.getName());

  public static ContentType TYPE = new ContentType(Resource.TYPE, TYPE_NAME);

  public static final String CHILDREN_COUNT = "childrenCount";

  protected long childrenCount;

  @Transient
  private Map<String, Resource> cache;

  @Transient
  private Map<String, Rendition> allRenditions;

  @Transient
  private Map<String, Rendition> localRenditions;

  @Transient
  private List<Rendition> remoteRenditions;

  public static final String HOME_WIDGET = "";

  private String html;

  public static final String HTML = "html";

  public Resource findChild(final String uri) {
    String[] segments = uri.split("/");
    Resource child = this;
    for (String path : segments) {
      if (path.length() == 0) {
        continue;
      }
      Content parent = (Content) child;
      child = parent.getRendition(path);
      if (child != null) {
        return child;
      } else if (parent instanceof Entry) {
        child = ((Entry) parent).getChild(path);
      } else if (parent instanceof Feed) {
        child = ((Feed) parent).getEntry(path);
      }
      if (child == null) {
        if (GWT.isClient()) {
          child = new ResourceProxy();
          child.setParent(parent);
        } else {
          break;
        }
      }
    }
    return child;
  }

  @XmlTransient
  public Map<String, Rendition> getAllRenditions() {
    if (allRenditions == null) {
      allRenditions = new HashMap<String, Rendition>();
      allRenditions.putAll(getLocalRenditions());
      if (remoteRenditions != null) {
        for (Rendition v : remoteRenditions) {
          allRenditions.put(v.getPath(), v);
        }
      }
    }
    return allRenditions;
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

  public String getHtml() {
    return html;
  }

  @XmlTransient
  public Map<String, Rendition> getLocalRenditions() {
    if (localRenditions == null) {
      localRenditions = new HashMap<String, Rendition>();
      for (String key : getResourceType().getWidgetKeys()) {
        if (key.equals(Content.HOME_WIDGET)) {
          continue;
        }
        Object widget = getResourceType().getWidget(key);
        if (widget != null) {
          Rendition view = new Rendition();
          view.setParent(this);
          view.setPath(key);
          view.setTitle(key);
          localRenditions.put(key, view);
        }
      }
    }
    return localRenditions;
  }

  @XmlTransient
  public List<Rendition> getRemoteRenditions() {
    return remoteRenditions;
  }

  public Rendition getRendition(String kind) {
    return getAllRenditions().get(kind);
  }

  @Override
  public ContentType<?> getResourceType() {
    return TYPE;
  }

  @Override
  public Object getWidget() {
    if (widget == null) {
      widget = getResourceType().getWidget(HOME_WIDGET);
    }
    return widget;
  }

  public void setChildrenCount(long totalResults) {
    this.childrenCount = totalResults;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  @Override
  public void setProperty(String name, String value) {
    if (CHILDREN_COUNT.equals(name)) {
      childrenCount = value == null ? 0 : Long.valueOf(value);
    } else {
      super.setProperty(name, value);
    }
  }

  public void setRemoteRenditions(List<Rendition> remoteViews) {
    this.remoteRenditions = remoteViews;
  }
}
