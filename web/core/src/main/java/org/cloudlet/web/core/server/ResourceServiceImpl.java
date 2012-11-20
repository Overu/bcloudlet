package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.google.web.bindery.autobean.vm.impl.TypeUtils;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.cloudlet.web.core.shared.ClassUtil;
import org.cloudlet.web.core.shared.DefaultField;
import org.cloudlet.web.core.shared.Relationship;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceService;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.WebPlatform;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

public class ResourceServiceImpl<T extends Resource> implements ResourceService<T> {

  private static final Logger logger = Logger.getLogger(ResourceServiceImpl.class.getName());

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
  protected ResourceServiceImpl() {
    Type genericSuperClass = getClass().getGenericSuperclass();
    resourceClass =
        (Class<T>) TypeUtils.ensureBaseType(TypeUtils.getSingleParameterization(
            ResourceService.class, genericSuperClass));
    resourceType = WebPlatform.getInstance().getResourceType(resourceClass);
  }

  @Override
  public long countChildren(T entry) {
    TypedQuery<Long> query =
        em().createQuery(
            "select count(o) from " + Relationship.class.getName() + " as o where o.source=:source",
            Long.class);
    query.setParameter("source", entry);
    return query.getSingleResult().longValue();
  }

  @Override
  @Transactional
  public <C extends Resource> C createChild(T entry, C child) {
    // check if child path conflicts
    if (child.getPath() != null && getChild(entry, child.getPath()) != null) {
      throw new EntityExistsException("A child of " + entry + " with path=" + child.getPath()
          + " already exists");
    }

    child.setParent(entry);
    child.save();

    Relationship rel = new Relationship();
    rel.setId(UUID.randomUUID().toString());
    rel.setSource(entry);
    rel.setTarget(child);
    rel.setPath(child.getPath());
    em().persist(rel);

    entry.setChildrenCount(entry.getChildrenCount() + 1);
    update(entry);

    return child;
  }

  @Override
  public Resource createFromMultipart(T parent, UriInfo uriInfo, final InputStream inputStream,
      final String contentType, final Integer contentLength) {
    MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
    try {
      FileUpload fileUpload = new FileUpload();
      FileItemIterator iter = fileUpload.getItemIterator(new RequestContext() {

        @Override
        public String getCharacterEncoding() {
          return "UTF-8";
        }

        @Override
        public int getContentLength() {
          return contentLength;
        }

        @Override
        public String getContentType() {
          return contentType;
        }

        @Override
        public InputStream getInputStream() throws IOException {
          return inputStream;
        }
      });

      Resource res = null;
      FileItemStream item = null;
      while (iter.hasNext()) {
        FileItemStream value = iter.next();
        String key = value.getFieldName();
        InputStream in = value.openStream();
        try {
          if (value.isFormField()) {
            String strValue = Streams.asString(in, "UTF-8");
            params.add(key, strValue);
          } else {
            item = value;
          }
        } finally {
          IOUtils.closeQuietly(in);
        }
      }
      if (item != null) {
        String rt = params.getFirst(Resource.RESOURCE_TYPE);
        ResourceType<Resource> resourceType = WebPlatform.getInstance().getResourceType(rt);
        res = resourceType.createInstance();
        res.setContentStream(item.openStream());
        res.setPath(item.getFieldName());
        res.setMimeType(item.getContentType());
        res.setParent(parent);
        res.readFrom(params);
        res.save();
      }
    } catch (Exception e) {
      // VirusFoundException, VirusFoundException will be handled by ServiceEndPointUtil centrally
      if (e.getCause() != null
          && e.getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
        throw new WebApplicationException();
      } else if (e instanceof FileUploadBase.SizeLimitExceededException) {
        throw new WebApplicationException();
      } else {
        throw new WebApplicationException();
      }
    } finally {
    }
    return null;
  }

  @Override
  @Transactional
  public void delete(T content) {
    em().remove(content);
  }

  @Override
  public <C extends Resource> C findChild(T entry, String path, Class<C> childType) {
    try {
      TypedQuery<C> query =
          em().createQuery(
              "from " + childType.getName() + " c where c.parent=:parent and c.path=:path",
              childType);
      query.setParameter("parent", entry);
      query.setParameter("path", path);
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public java.util.List<Resource> findChildren(T entry) {
    TypedQuery<Relationship> query =
        em().createQuery("from " + Relationship.class.getName() + " rel where rel.source=:source",
            Relationship.class);
    query.setParameter("source", entry);
    List<Relationship> rels = query.getResultList();
    List<Resource> children = new ArrayList<Resource>(rels.size());
    for (Relationship rel : rels) {
      children.add(rel.getTarget());
    }
    return children;
  }

  @Override
  public <C extends Resource> List<C> findChildren(T entry, Class<C> childType) {
    TypedQuery<C> query =
        em().createQuery("from " + childType.getName() + " c where c.parent=:parent", childType);
    query.setParameter("parent", entry);
    return query.getResultList();
  }

  @Override
  public T get() {
    Repository repo = WebPlatform.getInstance().getRepository();
    Path p = resourceClass.getAnnotation(Path.class);
    String path = p.value();
    T result = (T) repo.getChild(path);
    if (result == null) {
      result = ClassUtil.newInstance(resourceClass);
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

  @Override
  public T getById(String id, Class<T> type) {
    return em().find(type, id);
  }

  @Override
  public Resource getChild(T entry, String path) {
    try {
      TypedQuery<Relationship> query =
          em().createQuery(
              "from " + Relationship.class.getName()
                  + " rel where rel.source=:source and rel.path=:path", Relationship.class);
      query.setParameter("source", entry);
      query.setParameter("path", path);
      return query.getSingleResult().getTarget();
    } catch (NoResultException e) {
      return null;
    }
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
    // for (Method m : resource.getClass().getMethods()) {
    // if (m.getParameterTypes().length > 0) {
    // continue;
    // }
    // Path p = m.getAnnotation(Path.class);
    // Class<?> rt = m.getReturnType();
    // if (p != null && Resource.class.isAssignableFrom(rt)) {
    // Class<Resource> childType = (Class<Resource>) rt;
    // Resource result = ClassUtil.newInstance(childType);
    // result.setPath(p.value());
    // DefaultFields fields = m.getAnnotation(DefaultFields.class);
    // if (fields != null) {
    // for (DefaultField field : fields.value()) {
    // result.setPropertyValue(field.key(), field.value());
    // }
    // } else {
    // DefaultField field = m.getAnnotation(DefaultField.class);
    // if (field != null) {
    // result.setPropertyValue(field.key(), field.value());
    // }
    // }
    // resource.createChild(result);
    // }
    // }
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