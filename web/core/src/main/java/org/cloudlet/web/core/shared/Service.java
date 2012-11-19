package org.cloudlet.web.core.shared;

import com.google.inject.Provider;

import java.io.InputStream;

import javax.ws.rs.core.MultivaluedMap;

public interface Service<T extends Resource> extends Provider<T> {

  Resource createFromMultipart(T parent, MultivaluedMap<String, String> params, String contentType,
      Integer length, InputStream inputStream);

  void delete(T content);

  T getById(String id, Class<T> type);

  T save(T content);

  T update(T content);

}
