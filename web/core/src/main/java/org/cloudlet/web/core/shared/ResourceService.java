package org.cloudlet.web.core.shared;

import com.google.inject.Provider;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.UriInfo;

public interface ResourceService<T extends Resource> extends Provider<T> {

  long countChildren(T entry);

  <C extends Resource> C createChild(T entry, C child);

  Resource createFromMultipart(T parent, UriInfo uriInfo, InputStream inputStream,
      String contentType, Integer contentLength);

  void delete(T content);

  <C extends Resource> C findChild(T entry, String path, Class<C> childType);

  List<Resource> findChildren(T entry);

  <C extends Resource> List<C> findChildren(T entry, Class<C> childType);

  T getById(String id, Class<T> type);

  Resource getChild(T content, String path);

  T save(T content);

  T update(T content);

}
