package org.cloudlet.web.core.shared;

import com.google.gwt.user.client.ui.IsWidget;

public interface ResourceWidget<T extends Resource> extends IsWidget {

  T getResource();

  void setResource(T resource);

}
