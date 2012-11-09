package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Content;
import org.cloudlet.web.core.Entry;

import java.util.List;

public interface EntryService<E extends Entry> extends Service<E> {

  long countChildren(E entry);

  <C extends Content> C createChild(E entry, C child);

  Content findChild(E content, String path);

  <C extends Content> C findChild(E entry, String path, Class<C> childType);

  List<Content> findChildren(E entry);

}
