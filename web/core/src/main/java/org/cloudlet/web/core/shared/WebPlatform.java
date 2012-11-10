package org.cloudlet.web.core.shared;


import java.util.HashMap;
import java.util.Map;

public abstract class WebPlatform {

  private static WebPlatform instance;

  public static WebPlatform getInstance() {
    return instance;
  }

  private Class<? extends Repository> repositoryType;

  public Map<String, ObjectType> types = new HashMap<String, ObjectType>();

  private Map<String, Package> packages = new HashMap<String, Package>();

  public WebPlatform() {
    instance = this;
  }

  public void addType(ObjectType type) {
    types.put(type.getName(), type);
  }

  public ObjectType getByName(String name) {
    return types.get(name);
  }

  public abstract Object getObject(String type, String id);

  public ObjectType getObjectType(final String fullName) {
    WebType type = getType(fullName);
    return (ObjectType) type;
  }

  public Operation getOperation(final String fullName) {
    int index = fullName.lastIndexOf(".");
    String typeName = fullName.substring(0, index);
    ObjectType type = getObjectType(typeName);
    if (type != null) {
      String simpleName = fullName.substring(index + 1);
      return type.getOperation(simpleName);
    }
    return null;

  }

  public Package getPackage(final String name) {
    return packages.get(name);
  }

  public Map<String, Package> getPackages() {
    return packages;
  }

  public Class<? extends Repository> getRepositoryType() {
    return repositoryType;
  }

  public abstract <T extends Content> T getResource(Class<T> contentType);

  public abstract <S extends Service> S getService(Class<? extends Content> contentType);

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

  public void setRepositoryType(Class<? extends Repository> repositoryType) {
    this.repositoryType = repositoryType;
  }

}
