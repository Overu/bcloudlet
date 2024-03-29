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
package org.cloudlet.web.feature.client;

public class Connectivity {
  public interface Listener {
    void onOffline();

    void onOnline();
  }

  public static native void addEventListener(Listener listener) /*-{
                                                                $wnd.addEventListener("offline", function(e) {
                                                                listener.@com.goodow.web.feature.client.Connectivity.Listener::onOffline()();
                                                                }, false);
                                                                $wnd.addEventListener("online", function(e) {
                                                                  listener.@com.goodow.web.feature.client.Connectivity.Listener::onOnline()();
                                                                }, false);
                                                                }-*/;

}
