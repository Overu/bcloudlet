package org.cloudlet.web.core.client;

import com.google.gwt.user.client.Timer;

public abstract class Initializer {

  public Initializer() {
    new Timer() {
      @Override
      public void run() {
        init();
      }
    }.schedule(1);
  }

  protected abstract void init();

}