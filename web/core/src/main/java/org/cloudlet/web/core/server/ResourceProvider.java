package org.cloudlet.web.core.server;

import com.google.inject.Provider;

import java.util.logging.Logger;

import javax.ws.rs.Path;

public abstract class ResourceProvider<T extends Resource> implements Provider<T> {

  private static final Logger logger = Logger.getLogger(ResourceProvider.class.getName());

  protected Class<T> resourceClass;

  public ResourceProvider(Class<T> resourceClass) {
    this.resourceClass = resourceClass;
  }

  @Override
  public T get() {
    Path p = resourceClass.getAnnotation(Path.class);
    String path = p.value();
    Repository repo = WebPlatform.get().getRepository();
    T result = (T) repo.getChild(path);
    if (result == null) {
      result = WebPlatform.get().getInstance(resourceClass);
      result.setPath(path);
      DefaultField field = resourceClass.getAnnotation(DefaultField.class);
      if (field != null) {
        result.setPropertyValue(field.key(), field.value());
      }
      if (result.getTitle() == null) {
        result.setTitle(path);
      }
      repo.createChild(result);
    }
    return result;
  }
}
