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

public class MobileExplorer extends FlowPanel implements TakesResource, WidgetContainer {

  protected Resource resource;

  private ResourceManager resourceManager;

  private SimplePanel child;

  @Inject
  public MobileExplorer(ResourceManager rm, SimplePanel nav, SimplePanel child) {
    this.resourceManager = rm;
    this.child = child;
    add(nav);
    add(child);

    Button books = new Button("Books");
    nav.add(books);

    books.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        Resource books = resource.getChild("books");
        resourceManager.goTo(books);
      }
    });
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
  }

}
