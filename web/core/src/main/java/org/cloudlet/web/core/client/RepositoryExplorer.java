/*
 * Copyright 2012 Goodow.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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

import org.cloudlet.web.core.Repository;

public class RepositoryExplorer extends BorderLayoutContainer implements
    ResourceWidget<Repository>, ResourceContainer {

  SimpleContainer center;

  private final ResourceTree<Repository> placeTree;

  private ResourcePlace<Repository> place;

  @Inject
  public RepositoryExplorer(final ResourceTree<Repository> placeTree) {
    Window.enableScrolling(false);
    setPixelSize(Window.getClientWidth(), Window.getClientHeight());
    setMonitorWindowResize(true);

    this.placeTree = placeTree;

    StringBuffer sb = new StringBuffer();
    sb.append("<div id='demo-theme'></div><div id=demo-title>Retech Explorer Demo</div>");
    HtmlLayoutContainer northPanel = new HtmlLayoutContainer(sb.toString());
    northPanel.setStateful(false);
    northPanel.setId("demo-header");
    northPanel.addStyleName("x-small-editor");

    ContentPanel west = new ContentPanel();
    west.setBodyBorder(true);
    west.setWidget(placeTree);

    center = new SimpleContainer();

    BorderLayoutData northData = new BorderLayoutData(70);
    northData.setMargins(new Margins(5));

    BorderLayoutData westData = new BorderLayoutData(150);
    westData.setMargins(new Margins(0, 5, 5, 5));

    MarginData centerData = new MarginData(0, 5, 4, 0);

    setNorthWidget(northPanel, northData);
    setWestWidget(west, westData);
    setCenterWidget(center, centerData);
  }

  @Override
  public void addResourceWidget(final IsWidget w) {
    center.setWidget(w);
    if (w instanceof RequiresResize) {
      RequiresResize widget = (RequiresResize) w;
      widget.onResize();
    }
  }

  @Override
  public ResourcePlace<Repository> getPlace() {
    return place;
  }

  @Override
  public Class<Repository> getResourceType() {
    return Repository.class;
  }

  @Override
  public void setPlace(ResourcePlace<Repository> place) {
    this.place = place;
    placeTree.setPlace(place);
  }

  @Override
  protected void onWindowResize(final int width, final int height) {
    setPixelSize(width, height);
  }
}
