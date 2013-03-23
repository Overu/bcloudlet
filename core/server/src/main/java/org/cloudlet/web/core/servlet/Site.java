package org.cloudlet.web.core.servlet;

public class Site {

  private String id;

  private String url;

  private String mirror;

  private String localFile;

  public Site(String id, String url, String mirror) {
    this.id = id;
    this.url = url;
    this.mirror = mirror;
  }

  public String getId() {
    return id;
  }

  public String getLocalFile() {
    return localFile;
  }

  public String getMirror() {
    return mirror;
  }

  public String getUrl() {
    return url;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLocalFile(String localFile) {
    this.localFile = localFile;
  }

  public void setMirror(String mirror) {
    this.mirror = mirror;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
