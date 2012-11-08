package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Content;

public interface Service<T extends Content> {

  <CHILD extends Content> CHILD create(T parent, CHILD child);

  void delete(T content);

  T getById(String id, Class<T> type);

  <CHILD extends Content> CHILD getChild(T parent, String path, Class<CHILD> childType);

  T save(T content);

  T update(T content);

}
