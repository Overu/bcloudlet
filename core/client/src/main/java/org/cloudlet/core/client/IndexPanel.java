package org.cloudlet.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.util.DelayedTask;

import org.cloudlet.core.client.EventGenerator.Function;

import java.util.ArrayList;
import java.util.List;

public class IndexPanel extends CompositeView {

  interface Binder extends UiBinder<Widget, IndexPanel> {
  }

  interface IndexStyle extends CssResource {
    String active();

    String gotop();

    String gotopshow();
  }

  private static Binder binder = GWT.create(Binder.class);

  private final static int OFFSET = 49;

  @UiField
  DivElement content;
  @UiField
  IndexStyle style;
  @UiField
  UListElement ul;
  @UiField
  HTMLPanel root;

  @Inject
  private SimplePanel goTop;
  @Inject
  private LoginBar loginBar;

  private DelayedTask gotoTask;
  private DelayedTask navTask;

  private List<PositionNav> pns;
  private int navMix;
  private int navMax;
  private Element currentli;

  private int scrollTop;

  private boolean gotoShowing;

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    RootPanel.get().add(goTop);
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    RootPanel.get().remove(goTop);
  }

  @Override
  protected void start() {
    root.add(loginBar);

    goTop.addStyleName(style.gotop());
    goTop.addDomHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        root.getElement().setScrollTop(0);
      }
    }, ClickEvent.getType());

    gotoTask = new DelayedTask() {
      @Override
      public void onExecute() {
        if (!gotoShowing && scrollTop >= 800) {
          goTop.addStyleName(style.gotopshow());
          gotoShowing = true;
        } else if (scrollTop < 800 && gotoShowing) {
          goTop.removeStyleName(style.gotopshow());
          gotoShowing = false;
        }
      }
    };

    navTask = new DelayedTask() {
      @Override
      public void onExecute() {
        if (scrollTop >= navMix && scrollTop <= navMax) {
          return;
        }
        currentli.removeClassName(style.active());
        int i = 0;
        while (i < pns.size()) {
          PositionNav nav = pns.get(i);
          navMix = nav.getScrollTop() - OFFSET;
          navMax = ((i == (pns.size() - 1)) ? root.getElement().getScrollHeight() : pns.get(i + 1).getScrollTop()) - OFFSET;
          if (scrollTop >= navMix && scrollTop <= navMax) {
            currentli = nav.getElm();
            break;
          }
          i++;
        }
        currentli.addClassName(style.active());
      }
    };

    initPostion();

    root.addDomHandler(new ScrollHandler() {
      @Override
      public void onScroll(ScrollEvent event) {
        scrollTop = event.getRelativeElement().getScrollTop();
        gotoTask.delay(100);
        navTask.delay(30);
      }
    }, ScrollEvent.getType());
  }

  private void initPostion() {
    if (pns == null) {
      pns = new ArrayList<PositionNav>();
    }
    NodeList<Node> nodes = content.getChildNodes();
    for (int i = 1; i < nodes.getLength(); i = i + 2) {
      Element elm = nodes.getItem(i).<Element> cast();

      final int absoluteTop = elm.getAbsoluteTop() - OFFSET;

      LIElement li = Document.get().createLIElement().cast();
      li.setInnerHTML(elm.getId());
      EventGenerator.onClick(li, new Function() {
        @Override
        public void f(Event e) {
          root.getElement().setScrollTop(absoluteTop);
        }
      });
      ul.appendChild(li);

      pns.add(new PositionNav(absoluteTop, li));
    }
    currentli = pns.get(0).getElm();
    currentli.addClassName(style.active());
  }
}
