package org.cloudlet.web.core.server;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class Service {

  private static final Logger logger = Logger.getLogger(Service.class.getName());

  @Inject
  protected RepositoryService repositoryService;

  public void delete(Content bean) {
    em().remove(bean);
  }

  @Transactional
  protected Content createChild(Content parent, Content child) {
    child.setParent(parent);
    String id = child.getId();
    String path = child.getPath();
    if (path != null && parent != null) {
      Content exist = parent.getChild(path);
      if (exist != null) {
        throw new EntityExistsException("A child with path=" + path + " already exists");
      }
    }

    if (id == null) {
      child.setId(CoreUtil.randomID());
    }

    if (path == null) {
      child.setPath(child.getId());
    }

    em().persist(child);

    child.init();
    return child;
  }

  protected EntityManager em() {
    return WebPlatform.get().getEntityManager();
  }

  @Transactional
  protected Content update(Content content) {
    String id = content.getId();
    String path = content.getPath();
    Content parent = content.getParent();
    // TODO validation
    Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
    if (path == null) {
    }
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException("", violations);
    }
    if (parent != null) {
      Content exist = parent.getChild(path);
      if (exist != null && (id == null || !exist.equals(content))) {
        throw new EntityExistsException("A child with path=" + path + " already exists");
      }
    }
    em().persist(content);
    return content;
  }

}
