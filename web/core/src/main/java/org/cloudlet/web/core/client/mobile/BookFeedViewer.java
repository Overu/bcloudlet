package org.cloudlet.web.core.client.mobile;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import org.cloudlet.web.core.client.Resource;
import org.cloudlet.web.core.client.TakesResource;
import org.cloudlet.web.core.shared.CorePackage;

import java.util.List;

public class BookFeedViewer extends FlowPanel implements TakesResource {

  protected Resource resource;

  @Inject
  public BookFeedViewer() {
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource value) {
    this.resource = value;
    List<Resource> books = resource.getList(CorePackage.ENTRIES);
    for (Resource book : books) {
      BookSummary summaryView = new BookSummary();
      summaryView.setValue(book);
      add(summaryView);
    }
  }

}
