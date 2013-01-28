package org.cloudlet.web.core.client.mobile;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.web.core.client.Initializer;
import org.cloudlet.web.core.client.Registry;
import org.cloudlet.web.core.shared.CorePackage;

@Singleton
public class MobileApp extends Initializer {

  @Inject
  Provider<MobileExplorer> explorer;

  @Inject
  Provider<BookFeedViewer> bookFeed;

  @Override
  protected void init() {
    Registry.setWidget(CorePackage.Repository, CorePackage.HOME, explorer);
    Registry.setWidget(CorePackage.BookFeed, CorePackage.HOME, bookFeed);
  }
}