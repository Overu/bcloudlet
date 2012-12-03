package org.cloudlet.web.core;

import java.util.List;

public interface Feed<E extends Resource> extends Resource {

  List<E> getEntries();

  Long getQueryCount();

  void setEntries(List<E> entries);

  void setQueryCount(Long count);

}
