package org.cloudlet.web.core.shared;

public class ResourceProxy extends Resource {

  public ResourceProxy(Resource parent, String path) {
    this.parent = parent;
    this.path = path;
  }

}
