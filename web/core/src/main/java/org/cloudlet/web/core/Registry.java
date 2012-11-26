package org.cloudlet.web.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Registry {

  private static Map<String, Class<? extends Resource>> nameToClassMap = new HashMap<String, Class<? extends Resource>>();

  public static Map<String, Map<String, Object>> widgets = new HashMap<String, Map<String, Object>>();

  public static Class<? extends Resource> getResourceType(String name) {
    return nameToClassMap.get(name);
  }

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

  public static final Map<String, Object> getWidgets(String resourceType) {
    if (!widgets.containsKey(resourceType)) {
      return Collections.EMPTY_MAP;
    }
    return widgets.get(resourceType);
  }

  public static void register(String name, Class<? extends Resource> resourceType) {
    nameToClassMap.put(name, resourceType);
  }

  public static final void setWidget(Class<?> cls, String rendition, Object widget) {
    setWidget(cls.getName(), rendition, widget);
  }

  public static final void setWidget(String type, String rendition, Object widget) {
    Map<String, Object> typedWidgets = widgets.get(type);
    if (typedWidgets == null) {
      typedWidgets = new HashMap<String, Object>();
      widgets.put(type, typedWidgets);
    }
    typedWidgets.put(rendition, widget);
  }

}
