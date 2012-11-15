package org.cloudlet.web.core.shared;

import java.io.InputStream;

public interface ContentService<T extends Content> extends Service<T> {

  Resource createChild(T parent, String path, String title, String contentType, Integer length,
      InputStream inputStream);

}
