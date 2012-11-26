package org.cloudlet.web.core.service;

import com.google.inject.Provider;

import org.cloudlet.web.core.bean.DefaultField;
import org.cloudlet.web.core.bean.RepositoryBean;
import org.cloudlet.web.core.bean.ResourceBean;
import org.cloudlet.web.core.bean.WebPlatform;

import java.util.logging.Logger;

import javax.ws.rs.Path;

public abstract class ResourceProvider<T extends ResourceBean> implements Provider<T> {

  private static final Logger logger = Logger.getLogger(ResourceProvider.class.getName());

  protected Class<T> resourceClass;

  public ResourceProvider(Class<T> resourceClass) {
    this.resourceClass = resourceClass;
  }

  @Override
  public T get() {
    Path p = resourceClass.getAnnotation(Path.class);
    String path = p.value();
    RepositoryBean repo = WebPlatform.get().getRepository();
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
