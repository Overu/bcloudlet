package org.cloudlet.web.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import org.cloudlet.web.core.client.CompositeView;
import org.cloudlet.web.core.client.EventGenerator;
import org.cloudlet.web.core.client.EventGenerator.Function;
import org.cloudlet.web.core.client.Resource;
import org.cloudlet.web.core.server.Content;

import java.util.List;

public class CategoryView extends CompositeView implements SelectionHandler<Integer>, HasSelectionHandlers<Integer> {

  interface Binder extends UiBinder<Widget, CategoryView> {
  }

  interface CategoryViewStyle extends CssResource {
    String active();
  }

  private static Binder binder = GWT.create(Binder.class);

  @Inject
  ResourceData data;

  @UiField
  UListElement category;
  @UiField
  CategoryViewStyle style;

  private Integer oldIndex = -1;

  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  public void claer() {
    NodeList<Node> nodes = category.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      category.removeChild(nodes.getItem(i));
    }
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
  }

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    addSelectionHandler(this);
    generator(data.getCategory());
  }

  private void bindLiEvent(final Element elm, final int i) {
    EventGenerator.onClick(elm, new Function() {
      @Override
      public void f(Event e) {
        SelectionEvent.fire(CategoryView.this, new Integer(i));
      }
    });
  }

  private void generator(List<Resource> resources) {
    claer();
    int i = 0;
    for (final Resource rsource : resources) {
      LIElement li = Document.get().createLIElement();
      AnchorElement a = Document.get().createAnchorElement();
      a.setInnerText(rsource.getString(Content.TITLE));
      a.setPropertyObject(Content.PATH, rsource);
      bindLiEvent(a, i);
      li.appendChild(a);
      category.appendChild(li);
      i++;
    }
  }

  private void setSelection(Integer i, boolean isAdd) {
    if (i == -1) {
      return;
    }
    Element elm = category.getChild(i).<Element> cast().getFirstChildElement();
    if (isAdd) {
      elm.addClassName(style.active());
    } else {
      elm.removeClassName(style.active());
    }
  }
}
