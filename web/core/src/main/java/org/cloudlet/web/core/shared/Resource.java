package org.cloudlet.web.core.shared;

import java.util.Date;
import java.util.List;

public interface Resource {

  public static final String RENDITION = "rendition";

  public static final String CHILDREN = "children";

  public static final String SELF = "";

  List<Resource> getChildren();

  int getChildrenCount();

  Date getDateCreated();

  String getId();

  String getPath();

  String getRenditionKind();

  String getTitle();

  String getType();

  String getUri();

  boolean isLeaf();

  void setChildren(List<Resource> children);

  void setChildrenCount(int count);

  void setDateCreated(Date date);

  void setId(String id);

  void setLeaf(boolean value);

  void setPath(String path);

  void setRenditionKind(String kind);

  // MultivaluedMap<String, String> getQueryParameters();
  // void setQueryParameters(MultivaluedMap<String, String> params);

  void setTitle(String title);

  void setType(String value);

  void setUri(String uri);

}
