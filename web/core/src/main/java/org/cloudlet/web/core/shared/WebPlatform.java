package org.cloudlet.web.core.shared;

import java.util.HashMap;
import java.util.Map;

public abstract class WebPlatform {

  private static WebPlatform instance;

  public static WebPlatform getInstance() {
    return instance;
  }

  private Class<? extends Repository> repositoryType;

  public Map<String, ResourceType> resourceTypes = new HashMap<String, ResourceType>();

  private Map<String, Package> packages = new HashMap<String, Package>();

  public WebPlatform() {
    instance = this;
  }

  public void addType(ResourceType type) {
    resourceTypes.put(type.getName(), type);
  }

  public abstract Object getObject(String type, String id);

  public Operation getOperation(final String fullName) {
    int index = fullName.lastIndexOf(".");
    String typeName = fullName.substring(0, index);
    WebType type = getWebType(typeName);
    if (type != null && type instanceof ResourceType) {
      ResourceType resType = (ResourceType) type;
      String simpleName = fullName.substring(index + 1);
      return resType.getOperation(simpleName);
    }
    return null;

  }

  public Package getPackage(final String name) {
    return packages.get(name);
  }

  public Map<String, Package> getPackages() {
    return packages;
  }

  public abstract Repository getRepository();

  public Class<? extends Repository> getRepositoryType() {
    return repositoryType;
  }

  public abstract <T extends Resource> T getResource(Class<T> resourceType);

  public <T extends Resource> ResourceType<T> getResourceType(Class<T> cls) {
    return (ResourceType<T>) getType(cls.getName());
  }

  public ResourceType getResourceType(String name) {
    return resourceTypes.get(name);
  }

  public abstract <S extends ResourceService> S getService(Class<? extends Resource> contentType);

  public WebType getType(final String fullName) {
    int index = fullName.lastIndexOf(".");
    String pkgName = fullName.substring(0, index);
    Package pkg = getPackage(pkgName);
    if (pkg != null) {
      String simpleName = fullName.substring(index + 1);
      return pkg.getType(simpleName);
    }
    return null;
  }

  public WebType getWebType(Class cls) {
    WebType type = getType(cls.getName());
    return type;
  }

  public WebType getWebType(final String fullName) {
    WebType type = getType(fullName);
    return type;
  }

  public void setRepositoryType(Class<? extends Repository> repositoryType) {
    this.repositoryType = repositoryType;
  }

}
