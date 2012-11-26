package org.cloudlet.web.core;

import java.util.List;

public interface Feed<E extends Resource> extends Resource {

  List<E> getEntries();

  void setEntries(List<E> entries);

}
