/*
 * Copyright 2012 Retechcorp.com
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
package org.cloudlet.web.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.cloudlet.web.core.service.Service;

@Singleton
public class WebPlatform {

  @Inject
  private static WebPlatform instance;

  @Inject
  private static Injector injector;

  public static WebPlatform getInstance() {
    return instance;
  }

  public Injector getInjector() {
    return injector;
  }

  public <T extends Service<?>> T getSerivce(Class<T> type) {
    // TODO proxy-based creation
    return injector.getInstance(type);
  }
}
