package org.cloudlet.web.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import org.cloudlet.web.core.client.CompositeView;
import org.cloudlet.web.core.client.Resource;

import java.util.List;

public class BillBoardView extends CompositeView {

  interface BillBoardViewStyle extends CssResource {

    String textlist();

    String top3();

  }

  interface Binder extends UiBinder<Widget, BillBoardView> {
  }

  class TextList extends FlowPanel {

    private HeadingElement titleElm;
    private UListElement contentElm;

    public TextList(String title) {
      Document document = Document.get();

      DivElement header = document.createDivElement();

      titleElm = document.createHElement(3);
      titleElm.setInnerText(title);

      AnchorElement moreElm = document.createAnchorElement();
      moreElm.setInnerText("更多");

      contentElm = document.createULElement();
      contentElm.setAttribute("mobile", "");

      header.appendChild(titleElm);
      header.appendChild(moreElm);
      getElement().appendChild(header);
      getElement().appendChild(contentElm);

      this.addStyleName(style.textlist());
    }

    @Override
    public void clear() {
      NodeList<Node> nodes = contentElm.getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        contentElm.removeChild(nodes.getItem(i));
      }
    }

    public void refresh(List<Resource> resources) {
      clear();
      Document document = Document.get();
      int i = 0;
      for (Resource resource : resources) {
        if (i > 10) {
          break;
        }
        LIElement li = document.createLIElement();

        Element no = document.createElement("em");
        no.setInnerText(i + 1 + "");

        AnchorElement a = document.createAnchorElement();
        a.setInnerText(resource.getTitle());
        a.setTitle(resource.getTitle());

        li.appendChild(no);
        li.appendChild(a);
        contentElm.appendChild(li);
        if (i < 3) {
          no.addClassName(style.top3());
        }
        i++;
      }
    }
  }

  @UiField
  HTMLPanel root;
  @UiField
  BillBoardViewStyle style;

  @Inject
  ResourceData data;

  private static Binder binder = GWT.create(Binder.class);

  public TextList generator(String title, List<Resource> resouces) {
    TextList textList = new TextList(title);
    textList.refresh(resouces);
    return textList;
  }

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    List<Resource> billBoard = data.getBillBoard();
    root.add(generator("销售榜", billBoard));
    root.add(generator("月度榜", billBoard));
    root.add(generator("好评榜", billBoard));
  }

}
