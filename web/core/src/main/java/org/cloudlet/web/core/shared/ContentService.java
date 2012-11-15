package org.cloudlet.web.core.shared;

import java.io.InputStream;

import javax.ws.rs.core.MultivaluedMap;

public interface ContentService<T extends Content> extends Service<T> {

  Resource createChild(T parent, MultivaluedMap<String, String> params, String contentType,
      Integer length, InputStream inputStream);

}
