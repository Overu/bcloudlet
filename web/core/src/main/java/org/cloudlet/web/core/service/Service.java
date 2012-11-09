package org.cloudlet.web.core.service;

import org.cloudlet.web.core.Content;

public interface Service<T extends Content> {

  void delete(T content);

  T getById(String id, Class<T> type);

  T save(T content);

  T update(T content);

}
