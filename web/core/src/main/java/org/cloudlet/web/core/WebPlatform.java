package org.cloudlet.web.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

@Singleton
public final class WebPlatform {

  private static WebPlatform instance;

  public static WebPlatform get() {
    return instance;
  }

  @Inject
  @Transient
  private Provider<EntityManager> entityManager;

  private static final Logger logger = Logger.getLogger(WebPlatform.class.getName());

  @Inject
  private Injector injector;

  @Inject
  private Provider<Repository> repository;

  public WebPlatform() {
    instance = this;
  }

  public EntityManager getEntityManager() {
    return entityManager.get();
  }

  public <T extends Content> T getInstance(Class<T> resourceType) {
    return injector.getInstance(resourceType);
  }

  public Repository getRepository() {
    return repository.get();
  }

  public void injectMembers(Object instance) {
    injector.injectMembers(instance);
  }

}
