package org.cloudlet.core.client;

import com.google.gwt.dom.client.Element;

public class PositionNav {
  private int scrollTop;

  private Element elm;

  public PositionNav(int scrollTop, Element elm) {
    this.scrollTop = scrollTop;
    this.elm = elm;
  }

  public Element getElm() {
    return elm;
  }

  public int getScrollTop() {
    return scrollTop;
  }

  public void setElm(Element elm) {
    this.elm = elm;
  }

  public void setScrollTop(int scrollTop) {
    this.scrollTop = scrollTop;
  }

}
