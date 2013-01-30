package org.cloudlet.web.core.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class EventGenerator {

  public interface Function {
    public void f(Event e);
  }

  public static void bindEvent(Element elm, final int eventBus, final Function f) {
    DOM.sinkEvents(elm.<com.google.gwt.user.client.Element> cast(), eventBus);
    DOM.setEventListener(elm.<com.google.gwt.user.client.Element> cast(), new EventListener() {
      @Override
      public void onBrowserEvent(final Event event) {
        if (DOM.eventGetType(event) == eventBus) {
          f.f(event);
        }
      }
    });
  }

  public static void onClick(Element elm, Function f) {
    bindEvent(elm, Event.ONCLICK, f);
  }

  public static void onKeyUp(Element elm, Function f) {
    bindEvent(elm, Event.ONKEYUP, f);
  }
}
