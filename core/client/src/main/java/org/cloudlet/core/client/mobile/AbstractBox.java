package org.cloudlet.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.ModElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import org.cloudlet.core.client.CompositeView;
import org.cloudlet.core.client.EventGenerator;
import org.cloudlet.core.client.Resource;
import org.cloudlet.core.client.EventGenerator.Function;

import java.util.List;

public abstract class AbstractBox extends CompositeView {

  interface Binder extends UiBinder<Widget, AbstractBox> {
  }

  class BookView {
    private static final String RMB = "¥";

    private Resource book;

    public Resource getResource() {
      return book;
    }

    public LIElement init() {
      Function f = new Function() {
        @Override
        public void f(Event e) {
          Window.alert(book.getTitle());
        }
      };

      Document document = Document.get();
      AnchorElement imageAnchorElm = document.createAnchorElement();
      DivElement imageDivElm = document.createDivElement();
      Image image = new Image("");
      imageDivElm.appendChild(image.getElement());
      imageAnchorElm.appendChild(imageDivElm);
      imageAnchorElm.addClassName(style.cover());
      EventGenerator.onClick(imageAnchorElm, f);

      AnchorElement titleElm = document.createAnchorElement();
      titleElm.setInnerText(getResource().getTitle());
      titleElm.addClassName(style.title());
      EventGenerator.onClick(titleElm, f);

      DivElement priceElm = document.createDivElement();
      SpanElement currentPrice = document.createSpanElement();
      ModElement delPrice = document.createDelElement();
      currentPrice.setInnerText(RMB + "6");
      delPrice.setInnerText(RMB + "12");
      priceElm.appendChild(currentPrice);
      priceElm.appendChild(delPrice);
      priceElm.addClassName(style.price());

      LIElement liElm = document.createLIElement();
      liElm.appendChild(imageAnchorElm);
      liElm.appendChild(titleElm);
      if (isShow) {
        DivElement starDiv = document.createDivElement();
        SpanElement numSpan = document.createSpanElement();
        numSpan.setInnerText("( " + 6 + " )");
        starDiv.appendChild(recommendStar(0.5));
        starDiv.appendChild(numSpan);
        starDiv.addClassName(style.star());

        liElm.appendChild(starDiv);
      } else {
        ParagraphElement authorElm = document.createPElement();
        authorElm.setInnerText("Retech");
        liElm.appendChild(authorElm);
      }
      liElm.appendChild(priceElm);

      return liElm;
    }

    public void setResource(Resource book) {
      this.book = book;
    }

    private UListElement recommendStar(double number) {
      Document document = Document.get();
      UListElement starUl = document.createULElement();
      starUl.setAttribute("mobile", "");

      for (double i = 0.0; i < 5; i++) {
        LIElement statLi = document.createLIElement();
        statLi.addClassName(--number > 0 ? style.red() : (number == -0.5 ? style.half() : "null"));
        starUl.appendChild(statLi);
      }

      return starUl;
    }
  }

  interface BoxStryle extends CssResource {
    String cover();

    String half();

    String price();

    String red();

    String star();

    String title();
  }

  private static Binder binder = GWT.create(Binder.class);

  @UiField
  HeadingElement headNameElm;
  @UiField
  UListElement bookListElm;
  @UiField
  BoxStryle style;

  @Inject
  ResourceData data;

  private boolean isShow = false;

  public LIElement generateBox(Resource resource) {
    BookView bookView = new BookView();
    bookView.setResource(resource);
    return bookView.init();
  }

  public void setHeadName(String headName) {
    headNameElm.setInnerText(headName);
  }

  public void showOrHideRecommend(boolean isShow) {
    this.isShow = isShow;
  }

  @Override
  protected Widget initView() {
    return binder.createAndBindUi(this);
  }

  protected void refresh() {
    List<Resource> billBoard = data.getBillBoard();
    for (int i = 0; i < 10; i++) {
      bookListElm.appendChild(generateBox(billBoard.get(i)));
    }
  }

  @Override
  protected void start() {
    refresh();
  }
}
