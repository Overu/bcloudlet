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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

import org.cloudlet.web.core.shared.WebView;

public class GroupMainPanel extends WebView implements IsWidget, AcceptsOneWidget, EntryPoint {

  private DelayedTask windowResizeTask;
  private BorderLayoutContainer con;
  private int windowResizeDelay = !GWT.isScript() ? 100 : 0;

  ContentPanel center;

  public GroupMainPanel() {
    con = new BorderLayoutContainer();

    if (windowResizeTask == null) {
      windowResizeTask = new DelayedTask() {
        @Override
        public void onExecute() {
          onWindowResize(Window.getClientWidth(), Window.getClientHeight());
        }
      };
    }
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        windowResizeTask.delay(windowResizeDelay);
      }
    });

    Window.enableScrolling(false);
    con.setPixelSize(Window.getClientWidth(), Window.getClientHeight());

    con.setStateful(true);
    con.setStateId("explorerLayout");

    StringBuffer sb = new StringBuffer();
    sb.append("<div id='demo-theme'></div><div id=demo-title>Retech Explorer Demo</div>");
    HtmlLayoutContainer northPanel = new HtmlLayoutContainer(sb.toString());
    northPanel.setStateful(false);
    northPanel.setId("demo-header");
    northPanel.addStyleName("x-small-editor");

    ContentPanel west = new ContentPanel();
    center = new ContentPanel();
    center.setHeadingText("BorderLayout Example");

    BorderLayoutData northData = new BorderLayoutData(100);
    northData.setMargins(new Margins(5));

    BorderLayoutData westData = new BorderLayoutData(150);
    westData.setMargins(new Margins(0, 5, 5, 5));

    MarginData centerData = new MarginData(0, 5, 5, 0);

    con.setNorthWidget(northPanel, northData);
    con.setWestWidget(west, westData);
    con.setCenterWidget(center, centerData);

  }

  @Override
  public Widget asWidget() {
    return con;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(con);
  }

  @Override
  public void setWidget(IsWidget w) {
    center.setWidget(w);
  }

  protected void onWindowResize(final int width, final int height) {
    con.setPixelSize(width, height);
  }
}
