package org.cloudlet.web.core.bean;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.cloudlet.web.core.service.ResourceService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public final class WebPlatform {

  private static WebPlatform instance;

  public static WebPlatform getInstance() {
    return instance;
  }

  private Class<? extends RepositoryBean> repositoryType;

  private Map<String, Package> packages = new HashMap<String, Package>();

  private static final Logger logger = Logger.getLogger(WebPlatform.class.getName());

  @Inject
  private Injector injector;

  @Inject
  private Provider<RepositoryBean> repository;

  public WebPlatform() {
    instance = this;
  }

  public Object getObject(String type, String id) {
    try {
      Class<?> cls = Class.forName(type);
      if (Enum.class.isAssignableFrom(cls)) {
        Class<? extends Enum> enumClass = (Class<? extends Enum>) cls;
        return Enum.valueOf(enumClass, id);
      } else if (ResourceBean.class.isAssignableFrom(cls)) {
        ResourceService service = getService((Class<? extends ResourceBean>) cls);
        return service.getById(id, (Class<? extends ResourceBean>) cls);
      }
    } catch (ClassNotFoundException e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  public Package getPackage(final String name) {
    return packages.get(name);
  }

  public Map<String, Package> getPackages() {
    return packages;
  }

  public RepositoryBean getRepository() {
    return repository.get();
  }

  public Class<? extends RepositoryBean> getRepositoryType() {
    return repositoryType;
  }

  public <T extends ResourceBean> T getResource(Class<T> resourceType) {
    return injector.getInstance(resourceType);
  }

  public <S extends ResourceService> S getService(Class<? extends ResourceBean> contentType) {
    Class<S> serviceType = (Class<S>) getServiceType(contentType);
    if (serviceType == null) {
      System.out.println();
    }
    // TODO proxy-based creation
    return injector.getInstance(serviceType);
  }

  public <T extends ResourceService> Class<T> getServiceType(
      Class<? extends ResourceBean> contentClass) {
    Handler handler = contentClass.getAnnotation(Handler.class);
    if (handler != null) {
      return (Class<T>) handler.value();
    }
    Class superClass = contentClass.getSuperclass();
    if (ResourceBean.class.isAssignableFrom(superClass)) {
      return getServiceType(superClass);
    }
    // TODO proxy-based creation
    return null;
  }

  public void setRepositoryType(Class<? extends RepositoryBean> repositoryType) {
    this.repositoryType = repositoryType;
  }

}
