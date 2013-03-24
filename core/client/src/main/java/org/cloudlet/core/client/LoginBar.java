package org.cloudlet.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import org.cloudlet.core.client.EventGenerator.Function;

public class LoginBar extends CompositeView {

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
        loginBar.popupPanel.addStyleName(loginBar.style.hide());
        RootPanel.get().remove(loginBar.shadePanel);
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
    },
    POPUPLOGIN {
      @Override
      public void f(Event event, LoginBar loginBar) {
        loginBar.popupPanel.removeStyleName(loginBar.style.hide());
        RootPanel.get().add(loginBar.shadePanel);
      }
    },
    SEARCH {
      @Override
      public void f(Event event, LoginBar loginBar) {
        loginBar.hideLabel(loginBar.searchElm);
      }
    };

    public abstract void f(Event event, LoginBar loginBar);
  }

  interface LoginStyle extends CssResource {
    String hide();

    String shade();
  }

  private static class EmunFunction implements Function {

    public static LoginBar loginBar;

    private EventType type;

    public EmunFunction(EventType type) {
      this.type = type;
    }

    @Override
    public void f(Event e) {
      type.f(e, loginBar);
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
  Element pupupLogin;
  @UiField
  Element loginElm;
  @UiField
  InputElement emailElm;
  @UiField
  InputElement passwordElm;
  @UiField
  InputElement searchElm;
  @UiField
  HTMLPanel popupPanel;
  @UiField
  LoginStyle style;

  private SimplePanel shadePanel;

  public void bindClickEvent(Element elm, EmunFunction ef) {
    EventGenerator.onClick(elm, ef);
  }

  public void bindKeyUpEvent(Element elm, EmunFunction ef) {
    EventGenerator.onKeyUp(elm, ef);
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

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    EmunFunction.loginBar = this;

    popupPanel.addStyleName(style.hide());

    shadePanel = new SimplePanel();
    shadePanel.addStyleName(style.shade());

    bindClickEvent(sinaElm, new EmunFunction(EventType.SINA));
    bindClickEvent(renrenElm, new EmunFunction(EventType.RENREN));
    bindClickEvent(qqElm, new EmunFunction(EventType.QQ));
    bindClickEvent(doubanElm, new EmunFunction(EventType.DOUBAN));
    bindClickEvent(loginElm, new EmunFunction(EventType.LOGIN));
    bindClickEvent(pupupLogin, new EmunFunction(EventType.POPUPLOGIN));
    bindKeyUpEvent(emailElm, new EmunFunction(EventType.EMAIL));
    bindKeyUpEvent(passwordElm, new EmunFunction(EventType.PASSWORD));
    bindKeyUpEvent(searchElm, new EmunFunction(EventType.SEARCH));
  }
}
