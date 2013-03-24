package org.cloudlet.core.server;

import com.google.inject.Singleton;

@Singleton
public class CoreRepositoryProvider extends RepositoryProvider<CoreRepository> {

  @Override
  public Class<CoreRepository> getType() {
    return CoreRepository.class;
  }

}
