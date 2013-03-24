package org.cloudlet.core.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class CompositeView extends Composite {

  public CompositeView() {
    initWidget(initView());

    new Timer() {

      @Override
      public void run() {
        start();
      }
    }.schedule(1);
  }

  protected abstract Widget initView();

  protected abstract void start();

}
