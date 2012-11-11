package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType
@Entity
@Path("t_view")
public class View extends Resource {

  public static final ResourceType TYPE = new ResourceType(Resource.TYPE, "view");

  @Override
  public ResourceType getResourceType() {
    return TYPE;
  }

  @Override
  public StringBuilder getUriBuilder() {
    StringBuilder result = getParent().getUriBuilder();
    result.append(getWidgetKey());
    return result;
  }

  @Override
  public Object getWidget() {
    if (widget == null) {
      widget = getParent().getResourceType().getWidget(getWidgetKey());
    }
    return widget;
  }

  @Override
  public String getWidgetKey() {
    return path;
  }

  public void setWidgetKey(String key) {
    this.path = key;
  }

}
