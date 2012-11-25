package org.cloudlet.web.core.shared;

import java.util.List;

public interface Feed<E extends Resource> extends Resource {

  List<E> getEntries();

  void setEntries(List<E> entries);

}
