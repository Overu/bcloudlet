package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.IsWidget;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourcePlace;

public interface ResourceWidget<T extends Resource> extends IsWidget {

  ResourcePlace<T> getPlace();

  void setPlace(ResourcePlace<T> resource);

}
