package org.cloudlet.web.core.shared;


import java.util.List;

public interface EntryService<E extends Entry> extends Service<E> {

  long countRelationships(E entry);

  <C extends Content> C createRelationship(E entry, C child);

  Content getRelationship(E content, String path);

  <C extends Content> C findRelationship(E entry, String path, Class<C> childType);

  List<Content> findRelationships(E entry);

}
