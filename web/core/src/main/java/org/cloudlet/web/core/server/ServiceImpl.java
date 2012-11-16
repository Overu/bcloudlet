package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.google.web.bindery.autobean.vm.impl.TypeUtils;

import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.shared.ClassUtil;
import org.cloudlet.web.core.shared.DefaultField;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.WebPlatform;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ServiceImpl<T extends Resource> implements Service<T> {

  private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());

  public static File getFile(final Resource res) {
    String filePath = "D:/DevData/resource/" + res.getId();
    return new File(filePath);
  }

  public static void saveResource(final Resource resource, final InputStream inputStream) {
    InputStream in = null;
    OutputStream out = null;
    try {
      File file = getFile(resource);
      file.getParentFile().mkdirs();
      file.createNewFile();
      in = new BufferedInputStream(inputStream);
      out = new FileOutputStream(file);
      byte[] buffer = new byte[1024];
      for (int bytesRead = in.read(buffer); bytesRead > 0; bytesRead = in.read(buffer)) {
        out.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
  }

  public static void writeResource(final Resource resource, final OutputStream out)
      throws IOException {
    InputStream in = null;
    try {
      File file = getFile(resource);
      in = new BufferedInputStream(new FileInputStream(file));
      byte[] buffer = new byte[1024];
      for (int bytesRead = in.read(buffer); bytesRead > 0; bytesRead = in.read(buffer)) {
        out.write(buffer, 0, bytesRead);
      }
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }
  }

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
    T result = repo.getChild(path);
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
      repo.createChild(result);
    }
    return result;
  }

  @Override
  public T getById(String id, Class<T> type) {
    return em().find(type, id);
  }

  @Override
  @Transactional
  public T save(T resource) {
    if (resource.getId() == null) {
      resource.setId(UUID.randomUUID().toString());
    }
    if (resource.getPath() == null) {
      resource.setPath(resource.getId());
    }
    em().persist(resource);
    InputStream stream = resource.getContentStream();
    if (stream != null) {
      saveResource(resource, stream);
    }
    return resource;
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
