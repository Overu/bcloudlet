package org.cloudlet.web.core.client.mobile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import org.cloudlet.web.core.client.Resource;
import org.cloudlet.web.core.client.ResourceManager;
import org.cloudlet.web.core.client.TakesResource;
import org.cloudlet.web.core.server.Content;
import org.cloudlet.web.core.server.Folder;

import java.util.List;

public class BookFeedViewer extends FlowPanel implements TakesResource {

  protected Resource resource;

  private final static int DEFAULT = 5;

  private int display = 10;

  private BookSummary more;

  @Inject
  private ResourceManager resourceManager;

  @Inject
  private BookDetail bookDetail;

  public BookFeedViewer() {
    more = BookSummary.more();
    more.setLandscape(BookSummary.INSTANCE.landscape().root());
    more.addDomHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        display += DEFAULT;
        refresh();
      }
    }, ClickEvent.getType());
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource value) {
    this.resource = value;
    refresh();
  }

  private void refresh() {
    clear();
    List<Resource> books = resource.getList(Folder.ITEMS);
    for (int i = 0; i < display; i++) {
      if (i >= books.size()) {
        break;
      }
      final Resource book = books.get(i);
      BookSummary summaryView = new BookSummary();
      summaryView.setLandscape(BookSummary.INSTANCE.landscape().root());
      summaryView.setValue(book);
      add(summaryView);
      summaryView.addDomHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          bookDetail.setValue(book);
          resourceManager.goTo(book.getParent().getParent().getChild(Content.EDIT));
        }
      }, ClickEvent.getType());
    }
    if (display >= books.size()) {
      return;
    }
    add(more);
  }
}
