package org.cloudlet.web.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.cloudlet.web.core.service.Service;

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
      } else if (Content.class.isAssignableFrom(cls)) {
        Service service = getService((Class<? extends Content>) cls);
        return service.getById(id, (Class<? extends Content>) cls);
      }
    } catch (ClassNotFoundException e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  @Override
  public <T extends Content> T getResource(Class<T> contentType) {
    return injector.getInstance(contentType);
  }

  @Override
  public <S extends Service> S getService(Class<? extends Content> contentType) {
    Class<S> serviceType = (Class<S>) getServiceType(contentType);
    // TODO proxy-based creation
    return injector.getInstance(serviceType);
  }

  public <T extends Service> Class<T> getServiceType(Class<? extends Content> contentClass) {
    Handler handler = contentClass.getAnnotation(Handler.class);
    if (handler != null) {
      return (Class<T>) handler.value();
    }
    Class superClass = contentClass.getSuperclass();
    if (Content.class.isAssignableFrom(superClass)) {
      return getServiceType(superClass);
    }
    // TODO proxy-based creation
    return null;
  }

}
