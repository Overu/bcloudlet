package org.cloudlet.web.core.client.v2;

import com.google.gwt.user.client.ui.IsWidget;

public interface ResourceWidget extends IsWidget {

  Resource getResource();

  void setResource(Resource resource);

}
