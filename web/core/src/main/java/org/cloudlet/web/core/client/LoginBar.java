package org.cloudlet.web.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginBar extends Composite {

  interface Binder extends UiBinder<Widget, LoginBar> {
  }

  enum EventBind {
    SINA {
      @Override
      public void f(Event event) {
        Window.alert("sina");
      }
    },
    RENREN {
      @Override
      public void f(Event event) {
        Window.alert("renren");
      }
    },
    QQ {
      @Override
      public void f(Event event) {
        Window.alert("qq");
      }
    },
    DOUBAN {
      @Override
      public void f(Event event) {
        Window.alert("douban");
      }
    },
    LOGIN {
      @Override
      public void f(Event event) {
        Window.alert("Login");
      }
    };

    public abstract void f(Event event);
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

  public LoginBar() {
    initWidget(binder.createAndBindUi(this));
    addClickEvents(sinaElm, EventBind.SINA);
    addClickEvents(renrenElm, EventBind.RENREN);
    addClickEvents(qqElm, EventBind.QQ);
    addClickEvents(doubanElm, EventBind.DOUBAN);
    addClickEvents(loginElm, EventBind.LOGIN);
  }

  public void addClickEvents(Element elm, final EventBind oauth) {
    DOM.sinkEvents(elm.<com.google.gwt.user.client.Element> cast(), Event.ONCLICK);
    DOM.setEventListener(elm.<com.google.gwt.user.client.Element> cast(), new EventListener() {
      @Override
      public void onBrowserEvent(final Event event) {
        if (DOM.eventGetType(event) == Event.ONCLICK) {
          event.stopPropagation();
          oauth.f(event);
        }
      }
    });
  }
}
