package org.cloudlet.web.core.client.mobile;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;

import org.cloudlet.web.core.client.Resource;
import org.cloudlet.web.core.client.ResourceManager;
import org.cloudlet.web.core.client.TakesResource;
import org.cloudlet.web.core.client.WidgetContainer;
import org.cloudlet.web.core.shared.CorePackage;

public class MobileExplorer extends FlowPanel implements TakesResource, WidgetContainer {

  protected Resource resource;

  private ResourceManager resourceManager;

  private SimplePanel child;

  private static final String[] bookFilters = { CorePackage.FEATURED, CorePackage.PROMOTED, CorePackage.TAGGED };

  private FlowPanel nav;

  @Inject
  public MobileExplorer(ResourceManager rm, FlowPanel nav, SimplePanel child) {
    this.resourceManager = rm;
    this.nav = nav;
    this.child = child;
    add(nav);
    add(child);
  }

  @Override
  public void acceptWidget(IsWidget widget) {
    child.setWidget(widget);
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource value) {
    this.resource = value;
    nav.clear();
    Resource books = resource.getChild(CorePackage.BOOKS);
    for (String filter : bookFilters) {
      final Resource filteredBooks = books.getChild(filter);
      Button filteredBtn = new Button(filter);
      nav.add(filteredBtn);
      filteredBtn.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
          resourceManager.goTo(filteredBooks);
        }
      });
    }
  }

}
