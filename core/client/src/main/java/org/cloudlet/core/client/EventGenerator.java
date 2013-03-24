package org.cloudlet.core.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class EventGenerator {

  public static class BindFunction {
    private Function f;

    private int type;

    public BindFunction(Function f, int type) {
      this.f = f;
      this.type = type;
    }

    public Function getF() {
      return f;
    }

    public int getType() {
      return type;
    }

    public boolean hasEventType(final int etype) {
      return (type & etype) == type;
    }

    public void setF(Function f) {
      this.f = f;
    }

    public void setType(int type) {
      this.type = type;
    }
  }

  public interface Function {
    public void f(Event e);
  }

  private static JsMap<Integer, JsObjectArray<BindFunction>> events = JsMap.<Integer, JsObjectArray<BindFunction>> create().cast();

  public static void bindEvent(final Element elm, Function f, int eventBus) {
    final int hasCode = elm.hashCode();
    if (events.get(hasCode) == null) {
      JsObjectArray<BindFunction> functions = JsObjectArray.<BindFunction> create().cast();
      events.put(hasCode, functions);
    }

    JsObjectArray<BindFunction> functions = events.get(hasCode);
    functions.add(new BindFunction(f, eventBus));

    int eventBuses = 0;
    for (int i = 0; i < functions.length(); i++) {
      BindFunction bindFunction = functions.get(i);
      eventBuses |= bindFunction.getType();
    }

    DOM.sinkEvents(elm.<com.google.gwt.user.client.Element> cast(), eventBuses);
    DOM.setEventListener(elm.<com.google.gwt.user.client.Element> cast(), new EventListener() {
      @Override
      public void onBrowserEvent(final Event event) {
        if ("body".equalsIgnoreCase(elm.getTagName())) {
          return;
        }

        int eventGetType = DOM.eventGetType(event);
        if (events.get(hasCode) != null) {
          JsObjectArray<BindFunction> functions = events.get(hasCode);
          for (int i = 0; i < functions.length(); i++) {
            BindFunction listener = functions.get(i);
            if (listener.hasEventType(eventGetType)) {
              listener.getF().f(event);
            }
          }
        }
      }
    });
  }

  public static void onBlur(Element elm, Function f) {
    bindEvent(elm, f, Event.ONBLUR);
  }

  public static void onClick(Element elm, Function f) {
    bindEvent(elm, f, Event.ONCLICK);
  }

  public static void onFocus(Element elm, Function f) {
    bindEvent(elm, f, Event.ONFOCUS);
  }

  public static void onKeyDown(Element elm, Function f) {
    bindEvent(elm, f, Event.ONKEYDOWN);
  }

  public static void onKeyUp(Element elm, Function f) {
    bindEvent(elm, f, Event.ONKEYUP);
  }
}
