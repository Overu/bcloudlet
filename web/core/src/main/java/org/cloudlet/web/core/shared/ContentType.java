package org.cloudlet.web.core.shared;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class ContentType<T extends Content> extends ResourceType<T> {

  private Map<String, Object> widgets = new HashMap<String, Object>();

  public ContentType(ResourceType superType, String name) {
    super(superType, name);
  }

  public Object getWidget(String key) {
    Object result = widgets.get(key);
    if (result == null && superType != null && superType instanceof ContentType) {
      result = ((ContentType) superType).getWidget(key);
    }
    return result;
  }

  public Set<String> getWidgetKeys() {
    Set<String> result = new HashSet<String>();
    result.addAll(widgets.keySet());
    if (superType != null && superType instanceof ContentType) {
      result.addAll(((ContentType) superType).getWidgetKeys());
    }
    return result;
  }

  public void setWidget(String key, Object widget) {
    widgets.put(key, widget);
  }

}
