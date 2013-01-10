package org.cloudlet.web.core.client;

import java.util.HashMap;
import java.util.Map;

public class Registry {

  public static Map<String, Map<String, Object>> widgets = new HashMap<String, Map<String, Object>>();

  public static final Object getWidget(String type, String rendition) {
    if (rendition == null) {
      rendition = "";
    }
    Map<String, Object> typedWidgets = widgets.get(type);
    if (typedWidgets != null) {
      return typedWidgets.get(rendition);
    }
    return null;
  }

  public static final Map<String, Object> getWidgets(String type) {
    Map<String, Object> typedWidgets = widgets.get(type);
    if (typedWidgets == null) {
      typedWidgets = new HashMap<String, Object>();
      widgets.put(type, typedWidgets);
    }
    return typedWidgets;
  }

  public static final void setWidget(Class<?> cls, String rendition, Object widget) {
    setWidget(cls.getName(), rendition, widget);
  }

  public static final void setWidget(String type, String rendition, Object widget) {
    getWidgets(type).put(rendition, widget);
  }

}
