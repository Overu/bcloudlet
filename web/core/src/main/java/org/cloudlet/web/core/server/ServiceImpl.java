package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.google.web.bindery.autobean.vm.impl.TypeUtils;

import org.cloudlet.web.core.shared.ClassUtil;
import org.cloudlet.web.core.shared.DefaultField;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.WebPlatform;

import java.lang.reflect.Type;
import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.ws.rs.Path;

public class ServiceImpl<T extends Resource> implements Service<T> {

  private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

  @Inject
  protected Provider<EntityManager> entityManagerProvider;

  protected Class<T> resourceClass;

  protected ResourceType<T> resourceType;

  @SuppressWarnings("unchecked")
  protected ServiceImpl() {
    Type genericSuperClass = getClass().getGenericSuperclass();
    resourceClass =
        (Class<T>) TypeUtils.ensureBaseType(TypeUtils.getSingleParameterization(Service.class,
            genericSuperClass));
    resourceType = WebPlatform.getInstance().getResourceType(resourceClass);
  }

  @Override
  @Transactional
  public void delete(T content) {
    em().remove(content);
  }

  @Override
  public T get() {
    Repository repo = WebPlatform.getInstance().getRepository();
    Path p = resourceClass.getAnnotation(Path.class);
    String path = p.value();
    T result = repo.getRelationship(path);
    if (result == null) {
      result = ClassUtil.newInstance(resourceClass);
      result.setPath(path);
      DefaultField field = resourceClass.getAnnotation(DefaultField.class);
      if (field != null) {
        result.setProperty(field.key(), field.value());
      }
      if (result.getTitle() == null) {
        result.setTitle(path);
      }
      repo.createRelationship(result);
    }
    return result;
  }

  @Override
  public T getById(String id, Class<T> type) {
    return em().find(type, id);
  }

  @Override
  @Transactional
  public T save(T content) {
    if (content.getId() == null) {
      content.setId(UUID.randomUUID().toString());
    }
    if (content.getPath() == null) {
      content.setPath(content.getId());
    }
    em().persist(content);
    return content;
  }

  @Override
  @Transactional
  public T update(T content) {
    em().persist(content);
    return content;
  }

  protected EntityManager em() {
    return entityManagerProvider.get();
  }

}
