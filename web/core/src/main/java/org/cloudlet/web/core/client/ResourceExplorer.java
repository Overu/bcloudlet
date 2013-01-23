package org.cloudlet.web.core.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

import org.cloudlet.web.core.shared.Root;

public class ResourceExplorer extends BorderLayoutContainer implements WidgetContainer, TakesResource {

  SimpleContainer center;

  private final ResourceTree placeTree;

  private Resource resource;

  @Inject
  public ResourceExplorer(final LoginView loginView, final ResourceTree placeTree, @Root Resource root) {
    Window.enableScrolling(false);
    setPixelSize(Window.getClientWidth(), Window.getClientHeight());
    setMonitorWindowResize(true);

    this.placeTree = placeTree;
    placeTree.setValue(root);

    // StringBuffer sb = new StringBuffer();
    // sb.append("<div id='demo-theme'></div><div id=demo-title>Retech Explorer Demo</div>");
    // HtmlLayoutContainer northPanel = new HtmlLayoutContainer(sb.toString());
    // northPanel.setStateful(false);
    // northPanel.setId("demo-header");
    // northPanel.addStyleName("x-small-editor");

    ContentPanel west = new ContentPanel();
    west.setBodyBorder(true);
    west.setWidget(placeTree);

    center = new SimpleContainer();

    BorderLayoutData northData = new BorderLayoutData(30);
    northData.setMargins(new Margins(5));

    BorderLayoutData westData = new BorderLayoutData(150);
    westData.setMargins(new Margins(0, 5, 5, 5));

    MarginData centerData = new MarginData(0, 5, 4, 0);

    setNorthWidget(loginView, northData);
    setWestWidget(west, westData);
    setCenterWidget(center, centerData);
  }

  @Override
  public void acceptWidget(IsWidget widget) {
    center.setWidget(widget);
    if (widget instanceof RequiresResize) {
      RequiresResize resize = (RequiresResize) widget;
      resize.onResize();
    }
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource place) {
    this.resource = place;
    placeTree.setValue(place);
  }

  @Override
  protected void onWindowResize(final int width, final int height) {
    setPixelSize(width, height);
  }
}
