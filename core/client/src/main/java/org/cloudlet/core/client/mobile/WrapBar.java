package org.cloudlet.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import org.cloudlet.core.client.CompositeView;
import org.cloudlet.core.client.EventGenerator;
import org.cloudlet.core.client.EventGenerator.Function;

public class WrapBar extends CompositeView {

  interface Binder extends UiBinder<Widget, WrapBar> {
  }

  private static Binder binder = GWT.create(Binder.class);

  @UiField
  UListElement wrapElm;

  public void addBar(ImageResource image, Function f) {
    Image imageElm = new Image(image);
    initBar(imageElm, f);
  }

  public void addBar(String uri, Function f) {
    Image image = new Image(uri);
    initBar(image, f);
  }

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    Function f = new Function() {

      @Override
      public void f(Event e) {

      }
    };
    addBar("http://i.duokan.com/i/2013/02/6e96ef0ae722bdda.jpg", f);
    addBar("http://i.duokan.com/i/2013/02/6e96ef0ae722bdda.jpg", f);
    addBar("http://i.duokan.com/i/2013/02/6e96ef0ae722bdda.jpg", f);
    addBar("http://i.duokan.com/i/2013/02/6e96ef0ae722bdda.jpg", f);
  }

  private void initBar(Image imageElm, Function f) {
    Document document = Document.get();

    AnchorElement a = document.createAnchorElement();
    a.appendChild(imageElm.getElement());
    EventGenerator.onClick(a, f);

    LIElement li = document.createLIElement();
    li.appendChild(a);

    wrapElm.appendChild(li);
  }
}
