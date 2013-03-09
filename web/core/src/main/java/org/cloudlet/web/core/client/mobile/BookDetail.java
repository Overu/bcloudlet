package org.cloudlet.web.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import org.cloudlet.web.core.client.CompositeView;
import org.cloudlet.web.core.client.Resource;
import org.cloudlet.web.core.client.ResourceGrid;
import org.cloudlet.web.core.server.Book;

@Singleton
public class BookDetail extends CompositeView {

  interface Binder extends UiBinder<Widget, BookDetail> {
  }

  private static Binder binder = GWT.create(Binder.class);

  @UiField(provided = true)
  Button buyButton;
  @UiField(provided = true)
  Image bookImage;
  @UiField
  HTMLPanel detail;

  @UiField
  TableCellElement authorElm;

  private Resource book;

  public Resource getValue() {
    if (book == null) {
      return null;
    }
    return book;
  }

  public void refresh() {
    if (getValue() == null) {
      return;
    }
    String string = book.getString(Book.AUTHOR);
    authorElm.setInnerHTML(string);
  }

  public void setValue(Resource value) {
    book = value;
    refresh();
  }

  @Override
  protected Widget initView() {
    buyButton = new Button();
    bookImage = new Image(ResourceGrid.resources.cover());
    return binder.createAndBindUi(this);
  }

  @Override
  protected void start() {
    buyButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        Window.alert("buy!");
      }
    });

    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < 10; i++) {
      buffer
          .append("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tincidunt, arcu eget accumsan ullamcorper, ante nisl viverra enim, id consequat ante metus sed nibh. Sed orci nisl, dictum sit amet bibendum id, mollis ac arcu. Mauris venenatis orci sed dui lacinia vulputate. Proin in commodo nisl. Curabitur libero sem, tincidunt et eleifend et, euismod ac arcu. Etiam sit amet nulla mauris, id pulvinar enim. Quisque tincidunt accumsan tempor. Donec et euismod augue. Quisque ultricies mollis metus cursus consectetur. Ut sollicitudin magna in velit vulputate tempus. Sed metus metus, tincidunt nec consectetur vitae, sagittis ac velit Vestibulum consectetur, velit sed consectetur tempor, sapien odio tempor nulla, vel interdum mauris mi ac sem. Proin fermentum dictum mattis. Praesent eleifend posuere orci, vel rhoncus ante consequat eu. Vivamus eu nisl ornare nibh pellentesque fringilla. Sed tincidunt felis gravida mauris gravida sed venenatis mauris tincidunt. Pellentesque varius neque non arcu dictum consequat. Nulla vitae orci felis, ac egestas nisl. Vivamus semper sollicitudin mollis. Donec ac diam ut magna tempor consectetur. Donec at metus ligula, sed hendrerit sapien. Proin quis urna dui, id tincidunt tellus. Morbi enim ligula, mollis ut congue non, commodo nec magna. Quisque sagittis vehicula dui, ac aliquam tortor scelerisque a. Morbi urna ipsum, feugiat vitae fringilla in, blandit adipiscing tellus. Donec in est id tortor viverra viverra. Proin eu arcu sem, eget tincidunt est. Nunc in erat risus. Praesent pharetra pulvinar volutpat. Donec semper diam in enim luctus in viverra nisi tincidunt. Aliquam cursus interdum posuere.");

    }
    HTML html = new HTML(buffer.toString());
    detail.add(html);
  }
}