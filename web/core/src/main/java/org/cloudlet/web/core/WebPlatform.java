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

import org.cloudlet.web.core.service.Service;

import java.util.HashMap;
import java.util.Map;

public abstract class WebPlatform {

  private static WebPlatform instance;

  public static WebPlatform getInstance() {
    return instance;
  }

  private Class<? extends Repository> repositoryType;

  private Map<String, Package> packages = new HashMap<String, Package>();

  public WebPlatform() {
    instance = this;
  }

  public abstract Object getObject(String type, String id);

  public Map<String, Package> getPackages() {
    return packages;
  }

  public Class<? extends Repository> getRepositoryType() {
    return repositoryType;
  }

  public abstract <T extends Content> T getResource(Class<T> contentType);

  public abstract <S extends Service> S getService(Class<? extends Content> contentType);

  public void setRepositoryType(Class<? extends Repository> repositoryType) {
    this.repositoryType = repositoryType;
  }

}
