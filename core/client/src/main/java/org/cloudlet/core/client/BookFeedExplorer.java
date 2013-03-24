package org.cloudlet.core.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class BookFeedExplorer extends FeedExplorer {

  @Inject
  Provider<BookGrid> grid;

  @Override
  protected IsWidget createDefaultWidget() {
    return grid.get();
  }

}
