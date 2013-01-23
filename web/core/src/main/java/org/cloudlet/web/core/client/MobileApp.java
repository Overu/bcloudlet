package org.cloudlet.web.core.client;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.CorePackage;

@Singleton
public class MobileApp extends Initializer {

  @Inject
  Provider<ResourceExplorer> explorer;

  @Inject
  Provider<UserFeedExplorer> userFeed;

  @Inject
  Provider<BookFeedExplorer> bookFeed;

  @Override
  protected void init() {
    Registry.setWidget(CorePackage.Repository, CorePackage.HOME, explorer);
    Registry.setWidget(CorePackage.UserFeed, CorePackage.HOME, userFeed);
    Registry.setWidget(CorePackage.BookFeed, CorePackage.HOME, bookFeed);
  }
}