package org.cloudlet.web.core.client;

import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.WebPlatform;

@Singleton
public class ClientWebPlatform extends WebPlatform {

  @Override
  public Object getObject(String type, String id) {
    return null;
  }

  @Override
  public <T extends Resource> T getResource(Class<T> contentType) {
    return null;
  }

  @Override
  public <S extends Service> S getService(Class<? extends Resource> contentType) {
    return null;
  }

}
