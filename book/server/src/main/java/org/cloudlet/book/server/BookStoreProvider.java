package org.cloudlet.book.server;

import com.google.inject.Singleton;

import org.cloudlet.core.server.RepositoryProvider;

@Singleton
public class BookStoreProvider extends RepositoryProvider<BookStore> {

  @Override
  public Class<BookStore> getType() {
    return BookStore.class;
  }

}
