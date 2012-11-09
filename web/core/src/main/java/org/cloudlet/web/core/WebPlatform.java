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

import java.util.HashMap;
import java.util.Map;

@Singleton
public class WebPlatform {

  @Inject
  private static WebPlatform instance;

  @Inject
  private static Injector injector;

  @Inject
  public static WebPlatform getInstance() {
    return instance;
  }

  private Class<? extends Repository> repositoryType;

  private Map<String, Package> packages = new HashMap<String, Package>();

  public Map<String, Package> getPackages() {
    return packages;
  }

  public Class<? extends Repository> getRepositoryType() {
    return repositoryType;
  }

  public <T> T getService(Class<T> type) {
    // TODO proxy-based creation
    return injector.getInstance(type);
  }

  public void setRepositoryType(Class<? extends Repository> repositoryType) {
    this.repositoryType = repositoryType;
  }

}
