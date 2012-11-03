package org.cloudlet.web.core.shared;

public class WebView {

  public static final String FOLDER = "/";

  public static final String HOME = "";

  public static final String POST = "action=create";

  protected WebPlace place;

  public WebPlace getPlace() {
    return place;
  }

  public void setPlace(WebPlace place) {
    this.place = place;
  }

}
