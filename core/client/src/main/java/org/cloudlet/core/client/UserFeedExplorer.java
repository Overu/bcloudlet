package org.cloudlet.core.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserFeedExplorer extends FeedExplorer {
  @Inject
  Provider<UserGrid> userGrid;

  @Override
  protected IsWidget createDefaultWidget() {
    return userGrid.get();
  }

}
