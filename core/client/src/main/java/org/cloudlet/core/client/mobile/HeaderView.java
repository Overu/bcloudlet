package org.cloudlet.core.client.mobile;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import org.cloudlet.core.client.CompositeView;
import org.cloudlet.core.client.EventGenerator;
import org.cloudlet.core.client.EventGenerator.Function;

public class HeaderView extends CompositeView implements SelectionHandler<Integer>, HasSelectionHandlers<Integer> {

  public class SpanAnimation extends Animation {

    private boolean isShow = false;

    public boolean isShow() {
      return isShow;
    }

    public void showOrHide(boolean isShow) {
      cancel();
      this.isShow = isShow;
      run(500);
    }

    @Override
    protected void onComplete() {
      super.onComplete();
      Style style = searchButton.getStyle();
      if (isShow) {
        style.clearOpacity();
      } else {
        style.clearOpacity();
        style.setDisplay(Display.NONE);
      }
    }

    @Override
    protected void onStart() {
      super.onStart();
      if (isShow) {
        searchButton.getStyle().setDisplay(Display.BLOCK);
      }
    }

    @Override
    protected void onUpdate(double progress) {
      searchButton.getStyle().setOpacity(isShow ? progress : 1.0 - progress);
    }

  }

  interface Binder extends UiBinder<Widget, HeaderView> {
  }

  interface HeaderViewStyle extends CssResource {
    String inputting();

    String navactive();
  }

  private static Binder binder = GWT.create(Binder.class);

  @UiField
  DivElement logoElm;
  @UiField
  UListElement navUlElm;
  @UiField
  InputElement searchTextElm;
  @UiField
  SpanElement searchButton;

  @UiField
  HeaderViewStyle style;

  private Integer oldIndex = -1;

  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  @Override
  public void onSelection(SelectionEvent<Integer> event) {
    Integer newIndex = event.getSelectedItem();
    if (newIndex.equals(oldIndex)) {
      return;
    }
    setSelection(newIndex, true);
    setSelection(oldIndex, false);
    oldIndex = newIndex;
    switch (newIndex) {
      case 1:
        // Window.alert("index");
        break;
      case 3:
        // Window.alert("rank");
        break;
      case 5:
        // Window.alert("bargain");
        break;
      case 7:
        // Window.alert("category");
        break;
      case 9:
        // Window.alert("client");
        break;
      case 11:
        // Window.alert("bbs");
        break;
      default:
        break;
    }
  }

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    final Element aElm = searchTextElm.getParentElement();
    final SpanAnimation spanAnimation = new SpanAnimation();

    EventGenerator.onClick(logoElm, new Function() {
      @Override
      public void f(Event e) {
        Window.alert("index!");
      }
    });

    EventGenerator.onKeyUp(searchTextElm, new Function() {
      @Override
      public void f(Event e) {
        String test = searchTextElm.getValue();
        if (!spanAnimation.isShow() && test.length() > 0) {
          spanAnimation.showOrHide(true);
        } else if (test.equals("")) {
          spanAnimation.showOrHide(false);
        }
      }
    });
    EventGenerator.onFocus(searchTextElm, new Function() {
      @Override
      public void f(Event e) {
        if (aElm.getClassName().endsWith(style.inputting())) {
          return;
        }
        aElm.addClassName(style.inputting());
      }
    });
    EventGenerator.onBlur(searchTextElm, new Function() {
      @Override
      public void f(Event e) {
        if (searchTextElm.getValue().length() > 0) {
          return;
        }
        aElm.removeClassName(style.inputting());
      }
    });

    addSelectionHandler(this);

    for (int i = 1; i < navUlElm.getChildCount(); i += 2) {
      Element elm = navUlElm.getChild(i).<Element> cast();
      bindLiEvent(elm, i);
    }

  }

  private void bindLiEvent(final Element elm, final int i) {
    EventGenerator.onClick(elm, new Function() {
      @Override
      public void f(Event e) {
        SelectionEvent.fire(HeaderView.this, new Integer(i));
      }
    });
  }

  private void setSelection(Integer i, boolean isAdd) {
    if (i == -1) {
      return;
    }
    Element elm = navUlElm.getChild(i).<Element> cast();
    if (isAdd) {
      elm.addClassName(style.navactive());
    } else {
      elm.removeClassName(style.navactive());
    }
  }
}
