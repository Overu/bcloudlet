package org.cloudlet.web.core.shared;

import com.google.inject.Provider;

public interface Service<T extends Resource> extends Provider<T> {

  void delete(T content);

  T getById(String id, Class<T> type);

  T save(T content);

  T update(T content);

}
