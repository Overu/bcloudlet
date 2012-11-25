package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.IsWidget;

import org.cloudlet.web.core.Resource;

public interface ResourceWidget<T extends Resource> extends IsWidget {

  ResourcePlace<T> getPlace();

  void setPlace(ResourcePlace<T> resource);

}
