/*
 * Copyright 2012 Retechcorp.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.cloudlet.core.client;

import com.google.inject.Inject;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.form.FileUploadField;

public class MediaField extends AdapterField<Resource> {

  @Inject
  FileUploadField fileField;

  @Inject
  TextButton uploadButton;

  @Inject
  protected HorizontalLayoutContainer layoutContainer;

  private Resource media;

  private boolean initialized = false;

  @Inject
  ResourceManager resourceManager;

  public MediaField() {
    super(new SimpleContainer());
  }

  public String getName() {
    return fileField.getName();
  }

  @Override
  public Resource getValue() {
    return media;
  }

  @Override
  public SimpleContainer getWidget() {
    return (SimpleContainer) super.getWidget();
  }

  public void setName(String name) {
    fileField.setName(name);
  }

  @Override
  public void setValue(Resource value) {
    this.media = value;
  }

  protected void initView() {
    getWidget().setWidget(layoutContainer);
    fileField.setAllowBlank(false);
    layoutContainer.add(fileField, new HorizontalLayoutData(200, 1, new Margins(0, 8, 0, 0)));
    uploadButton.setText("Upload");
    layoutContainer.add(uploadButton);
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    ensureInitialized();
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }

}
