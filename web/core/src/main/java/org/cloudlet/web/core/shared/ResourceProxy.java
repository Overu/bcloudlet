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
package org.cloudlet.web.core.shared;

public class ResourceProxy extends Resource {

  private Resource realResource;

  private boolean loaded;

  public ResourceProxy() {
  }

  /**
   * @return the realContent
   */
  public Resource getRealResource() {
    return realResource;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.cloudlet.web.core.shared.Resource#getUriBuilder()
   */
  @Override
  public StringBuilder getUriBuilder() {
    StringBuilder builder = getParent().getUriBuilder();
    builder.append(path);
    return builder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.cloudlet.web.core.shared.Resource#getWidgetId()
   */
  @Override
  public String getRenditionKind() {
    return realResource.getRenditionKind();
  }

  /**
   * @return the loaded
   */
  public boolean isLoaded() {
    return loaded;
  }

  /**
   * @param loaded the loaded to set
   */
  public void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }

  /**
   * @param realContent the realContent to set
   */
  public void setRealResource(Resource realContent) {
    this.realResource = realContent;
  }

}
