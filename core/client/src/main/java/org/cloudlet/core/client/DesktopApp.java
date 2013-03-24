package org.cloudlet.core.client;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.core.server.Books;
import org.cloudlet.core.server.Content;
import org.cloudlet.core.server.Folder;
import org.cloudlet.core.server.Repository;
import org.cloudlet.core.server.Users;

@Singleton
public class DesktopApp extends Initializer {

  @Inject
  Provider<ResourceExplorer> explorer;

  @Inject
  Provider<UserEditor> userEditor;

  @Inject
  Provider<UserFeedExplorer> userFeed;

  @Inject
  Provider<BookEditor> bookEditor;

  @Inject
  Provider<BookFeedExplorer> bookFeed;

  @Override
  protected void init() {
    Registry.setWidget(Repository.TYPE_NAME, Content.HOME, explorer);
    Registry.setWidget(Users.TYPE_NAME, Content.HOME, userFeed);
    Registry.setWidget(Users.TYPE_NAME, Folder.NEW, userEditor);
    Registry.setWidget(Books.TYPE_NAME, Content.HOME, bookFeed);
    Registry.setWidget(Books.TYPE_NAME, Folder.NEW, bookEditor);
  }
}