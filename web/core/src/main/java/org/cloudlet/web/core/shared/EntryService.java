package org.cloudlet.web.core.shared;

import java.util.List;

public interface EntryService<E extends Entry> extends ContentService<E> {

  long countChildren(E entry);

  <C extends Resource> C createChild(E entry, C child);

  <C extends Resource> C findChild(E entry, String path, Class<C> childType);

  List<Resource> findChildren(E entry);

  <C extends Resource> List<C> findChildren(E entry, Class<C> childType);

  Resource getChild(E content, String path);

}
