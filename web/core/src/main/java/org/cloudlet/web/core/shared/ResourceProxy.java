package org.cloudlet.web.core.shared;

public class ResourceProxy extends Resource {

  private ResourceType<Resource> type;

  public ResourceProxy() {
    type = Resource.TYPE;
  }

  public ResourceProxy(ResourceType type) {
    this.type = type;
  }

  @Override
  public ResourceType<Resource> getResourceType() {
    return type;
  }

  public void setResourceType(ResourceType type) {
    this.type = type;
  }

}
