package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.Handler;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.WebPlatform;

import java.util.logging.Logger;

@Singleton
public class WebServerPlatform extends WebPlatform {

  private static final Logger logger = Logger.getLogger(WebServerPlatform.class.getName());

  @Inject
  private Injector injector;

  @Override
  public Object getObject(String type, String id) {
    try {
      Class<?> cls = Class.forName(type);
      if (Enum.class.isAssignableFrom(cls)) {
        Class<? extends Enum> enumClass = (Class<? extends Enum>) cls;
        return Enum.valueOf(enumClass, id);
      } else if (Resource.class.isAssignableFrom(cls)) {
        Service service = getService((Class<? extends Resource>) cls);
        return service.getById(id, (Class<? extends Resource>) cls);
      }
    } catch (ClassNotFoundException e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  @Override
  public <T extends Resource> T getResource(Class<T> contentType) {
    return injector.getInstance(contentType);
  }

  @Override
  public <S extends Service> S getService(Class<? extends Resource> contentType) {
    Class<S> serviceType = (Class<S>) getServiceType(contentType);
    // TODO proxy-based creation
    return injector.getInstance(serviceType);
  }

  public <T extends Service> Class<T> getServiceType(Class<? extends Resource> contentClass) {
    Handler handler = contentClass.getAnnotation(Handler.class);
    if (handler != null) {
      return (Class<T>) handler.value();
    }
    Class superClass = contentClass.getSuperclass();
    if (Resource.class.isAssignableFrom(superClass)) {
      return getServiceType(superClass);
    }
    // TODO proxy-based creation
    return null;
  }

}
