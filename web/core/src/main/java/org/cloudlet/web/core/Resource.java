package org.cloudlet.web.core;

import java.util.Date;
import java.util.List;

public interface Resource {

  String ID = "id";

  String TITLE = "title";

  String PATH = "path";

  String URI = "uri";

  String VERSION = "version";

  String RENDITION = "rendition";

  String CHILDREN = "children";

  String SELF = "";

  String RESOURCE_TYPE = "resourceType";

  String PARENT_TYPE = "parentType";

  String PARENT_ID = "parentId";

  List<Resource> getChildren();

  int getChildrenCount();

  Date getDateCreated();

  String getId();

  User getOwner();

  String getParentId();

  String getParentType();

  String getPath();

  String getRenditionKind();

  String getResourceType();

  String getTitle();

  String getUri();

  boolean isLeaf();

  void setChildren(List<Resource> children);

  void setChildrenCount(int count);

  void setDateCreated(Date date);

  void setId(String id);

  void setLeaf(boolean value);

  void setOwner(User user);

  void setParentId(String value);

  void setParentType(String value);

  void setPath(String path);

  void setRenditionKind(String kind);

  void setResourceType(String value);

  void setTitle(String title);

  void setUri(String uri);

}
