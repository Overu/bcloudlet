package org.cloudlet.web.core.server;

import com.google.inject.persist.Transactional;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.DefaultField;
import org.cloudlet.web.core.shared.DefaultFields;
import org.cloudlet.web.core.shared.Entry;
import org.cloudlet.web.core.shared.EntryService;
import org.cloudlet.web.core.shared.Relationship;
import org.cloudlet.web.core.shared.WebPlatform;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Path;

public class EntryServiceImpl<E extends Entry> extends ServiceImpl<E> implements EntryService<E> {

  @Override
  public long countRelationships(E entry) {
    TypedQuery<Long> query =
        em().createQuery(
            "select count(o) from " + Relationship.class.getName() + " as o where o.source=:source",
            Long.class);
    query.setParameter("source", entry);
    return query.getSingleResult().longValue();
  }

  @Override
  @Transactional
  public <C extends Resource> C createRelationship(E entry, C child) {
    // check if child path conflicts
    if (child.getPath() != null && getRelationship(entry, child.getPath()) != null) {
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
  public Resource getRelationship(E entry, String path) {
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
  public <C extends Resource> C findRelationship(E entry, String path, Class<C> childType) {
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
  public java.util.List<Resource> findRelationships(E entry) {
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
  @Transactional
  public E save(E entry) {
    super.save(entry);
    for (Method m : entry.getClass().getMethods()) {
      if (m.getParameterTypes().length > 0) {
        continue;
      }
      Path p = m.getAnnotation(Path.class);
      Class<?> rt = m.getReturnType();
      if (p != null && Resource.class.isAssignableFrom(rt)) {
        Class<Resource> childType = (Class<Resource>) rt;
        Resource result = WebPlatform.getInstance().getResource(childType);
        result.setPath(p.value());
        DefaultFields fields = m.getAnnotation(DefaultFields.class);
        if (fields != null) {
          for (DefaultField field : fields.value()) {
            result.setProperty(field.key(), field.value());
          }
        } else {
          DefaultField field = m.getAnnotation(DefaultField.class);
          if (field != null) {
            result.setProperty(field.key(), field.value());
          }
        }
        entry.createRelationship(result);
      }
    }
    return entry;
  }
}
