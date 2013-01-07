package org.cloudlet.web.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.RowHoverEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.impl.DOMImpl;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import com.sencha.gxt.widget.core.client.info.Info;

public class LoginBar extends Composite {

  interface Binder extends UiBinder<Widget, LoginBar> {
  }

  enum EventType {
    SINA {
      @Override
      public void f(Event event, LoginBar loginBar) {
        Window.alert("sina");
      }
    },
    RENREN {
      @Override
      public void f(Event event, LoginBar loginBar) {
        Window.alert("renren");
      }
    },
    QQ {
      @Override
      public void f(Event event, LoginBar loginBar) {
        Window.alert("qq");
      }
    },
    DOUBAN {
      @Override
      public void f(Event event, LoginBar loginBar) {
        Window.alert("douban");
      }
    },
    LOGIN {
      @Override
      public void f(Event event, LoginBar loginBar) {
        Window.alert("Login");
      }
    },
    EMAIL {
      @Override
      public void f(Event event, LoginBar loginBar) {
        loginBar.hideLabel(loginBar.emailElm);
      }
    },
    PASSWORD {
      @Override
      public void f(Event event, LoginBar loginBar) {
        loginBar.hideLabel(loginBar.passwordElm);
      }
    };

    private Element elm;

    public abstract void f(Event event, LoginBar loginBar);

    public Element getElm() {
      return elm;
    }

    public EventType bindElm(Element elm) {
      this.elm = elm;
      return this;
    }
  }

  private static Binder binder = GWT.create(Binder.class);

  @UiField
  DivElement sinaElm;
  @UiField
  DivElement renrenElm;
  @UiField
  DivElement qqElm;
  @UiField
  DivElement doubanElm;
  @UiField
  Element loginElm;
  @UiField
  InputElement emailElm;
  @UiField
  InputElement passwordElm;

  public LoginBar() {
    initWidget(binder.createAndBindUi(this));
    bindClickEvent(EventType.SINA.bindElm(sinaElm));
    bindClickEvent(EventType.RENREN.bindElm(renrenElm));
    bindClickEvent(EventType.QQ.bindElm(qqElm));
    bindClickEvent(EventType.DOUBAN.bindElm(doubanElm));
    bindClickEvent(EventType.LOGIN.bindElm(loginElm));
    bindKeyUpEvent(EventType.EMAIL.bindElm(emailElm));
    bindKeyUpEvent(EventType.PASSWORD.bindElm(passwordElm));
  }

  public void bindClickEvent(final EventType oauth) {
    bindEvent(oauth, Event.ONCLICK);
  }

  public void bindKeyUpEvent(final EventType oauth) {
    bindEvent(oauth, Event.ONKEYUP);
  }

  protected void hideLabel(InputElement inputElm) {
    String innerText = inputElm.getValue();
    Style style = inputElm.getNextSiblingElement().getStyle();
    if (innerText.length() == 0) {
      style.clearVisibility();
      return;
    }
    if (style.getVisibility().equals("") && innerText.length() > 0) {
      style.setVisibility(Visibility.HIDDEN);
    }
  }

  private void bindEvent(final EventType oauth, final int eventBus) {
    DOM.sinkEvents(oauth.getElm().<com.google.gwt.user.client.Element> cast(), eventBus);
    DOM.setEventListener(oauth.getElm().<com.google.gwt.user.client.Element> cast(), new EventListener() {
      @Override
      public void onBrowserEvent(final Event event) {
        if (DOM.eventGetType(event) == eventBus) {
          oauth.f(event, LoginBar.this);
        }
      }
    });
  }
}
