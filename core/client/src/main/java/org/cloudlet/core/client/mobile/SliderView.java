package org.cloudlet.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import org.cloudlet.core.client.CompositeView;
import org.cloudlet.core.client.EventGenerator;
import org.cloudlet.core.client.EventGenerator.Function;

public class SliderView extends CompositeView implements SelectionHandler<Integer>, HasSelectionHandlers<Integer> {

  interface Binder extends UiBinder<Widget, SliderView> {
  }

  interface Bundle extends ClientBundle {
    ImageResource slider();
  }

  interface SliderViewStyle extends CssResource {
    String currenttag();
  }

  private static Binder binder = GWT.create(Binder.class);
  private static Bundle bundle = GWT.create(Bundle.class);

  private int width;
  private int height;

  @UiField
  HTMLPanel root;
  @UiField
  UListElement slider;
  @UiField
  UListElement sliderTag;
  @UiField
  AnchorElement previous;
  @UiField
  AnchorElement next;
  @UiField
  SliderViewStyle style;

  private int currentTag = -1;
  private int sliderCount;
  private int oldTag;

  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  public void addSlider(ImageResource image, Function f) {
    Document document = Document.get();
    LIElement sliderField = document.createLIElement();
    EventGenerator.onClick(sliderField, f);
    Image imageField = new Image(image);
    setElmSize(imageField.getElement(), width, height);
    sliderField.appendChild(imageField.getElement());
    slider.appendChild(sliderField);
    setElmSize(slider, slider.getChildCount() * width, height);

    LIElement sliderTagField = document.createLIElement();
    final AnchorElement aTagField = document.createAnchorElement();
    aTagField.setInnerText(sliderTag.getChildCount() + 1 + "");
    EventGenerator.onClick(aTagField, new Function() {
      @Override
      public void f(Event e) {
        SelectionEvent.fire(SliderView.this, new Integer(aTagField.getInnerText()));
      }
    });
    sliderTagField.appendChild(aTagField);
    sliderTag.appendChild(sliderTagField);
  }

  public void hideTag(boolean show) {
    sliderTag.getStyle().setDisplay(show ? Display.BLOCK : Display.NONE);
  }

  public void move(int i) {
    sliderCount = slider.getChildCount() - 1;
    if (i < 0 || i > sliderCount || i == currentTag) {
      return;
    }
    Style preStyle = previous.getStyle();
    Style nextStyle = next.getStyle();

    moveTag(i);
    currentTag = i;
    slider.getStyle().setLeft(-currentTag * width, Unit.PX);

    preStyle.setVisibility(i == 0 ? Visibility.HIDDEN : Visibility.VISIBLE);
    nextStyle.setVisibility(i == sliderCount ? Visibility.HIDDEN : Visibility.VISIBLE);
  }

  @Override
  public void onSelection(SelectionEvent<Integer> event) {
    move(event.getSelectedItem() - 1);
  }

  @Override
  public void setPixelSize(int width, int height) {
    super.setPixelSize(width, height);
    this.width = width;
    this.height = height;
  }

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    setPixelSize(800, 200);
    EventGenerator.onClick(previous, new Function() {

      @Override
      public void f(Event e) {
        pre();
      }
    });
    EventGenerator.onClick(next, new Function() {

      @Override
      public void f(Event e) {
        next();
      }
    });
    addSlider(bundle.slider(), new Function() {

      @Override
      public void f(Event e) {
        Window.alert("a");
      }
    });
    addSlider(bundle.slider(), new Function() {

      @Override
      public void f(Event e) {
        Window.alert("a");
      }
    });
    addSlider(bundle.slider(), new Function() {
      @Override
      public void f(Event e) {
        Window.alert("a");
      }
    });
    addSelectionHandler(this);
    move(0);
  }

  private void moveTag(int i) {
    sliderTag.getChild(oldTag).<Element> cast().removeClassName(style.currenttag());
    oldTag = i;
    sliderTag.getChild(i).<Element> cast().addClassName(style.currenttag());
  }

  private void next() {
    int i = currentTag;
    if (++i > sliderCount) {
      currentTag = sliderCount;
      return;
    }
    move(i);
  }

  private void pre() {
    int i = currentTag;
    if (--i < 0) {
      currentTag = 0;
      return;
    }
    move(i);
  }

  private void setElmSize(Element elm, int width, int height) {
    DOM.setStyleAttribute(elm.<com.google.gwt.user.client.Element> cast(), "width", String.valueOf(width) + Unit.PX);
    DOM.setStyleAttribute(elm.<com.google.gwt.user.client.Element> cast(), "height", String.valueOf(height) + Unit.PX);
  }
}
