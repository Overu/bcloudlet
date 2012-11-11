package org.cloudlet.web.core.shared;


import java.util.List;

public interface EntryService<E extends Entry> extends Service<E> {

  long countRelationships(E entry);

  <C extends Resource> C createRelationship(E entry, C child);

  Resource getRelationship(E content, String path);

  <C extends Resource> C findRelationship(E entry, String path, Class<C> childType);

  List<Resource> findRelationships(E entry);

}
