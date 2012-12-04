package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

import org.cloudlet.web.core.Media;

public class ImageField extends MediaField {

  @Inject
  Image image;

  @Override
  public void setValue(Media value) {
    super.setValue(value);
    if (value != null) {
      String url = ResourcePlace.getMediaUrl(value);
      image.setUrl(url);
    } else {
      // image.setResource(placeHolderImage);
    }
  }

  @Override
  protected void initView() {
    super.initView();
    layoutContainer.add(image);
  }

}
