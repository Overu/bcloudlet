package org.cloudlet.web.core.client;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceService;
import org.cloudlet.web.core.shared.WebPlatform;

@Singleton
public class ClientWebPlatform extends WebPlatform {

  private Repository repository;

  @Override
  public Object getObject(String type, String id) {
    return null;
  }

  @Override
  public Repository getRepository() {
    if (repository == null) {
      repository = new Repository();
    }
    return repository;
  }

  @Override
  public <T extends Resource> T getResource(Class<T> contentType) {
    return null;
  }

  @Override
  public <S extends ResourceService> S getService(Class<? extends Resource> contentType) {
    return null;
  }

}
