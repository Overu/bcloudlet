package org.cloudlet.book.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.core.client.FeedExplorer;

public class BookFeedExplorer extends FeedExplorer {

  @Inject
  Provider<BookGrid> grid;

  @Override
  protected IsWidget createDefaultWidget() {
    return grid.get();
  }

}
