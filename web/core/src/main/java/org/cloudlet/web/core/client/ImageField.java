package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;

public class ImageField extends MediaField {

  public static String getMediaUrl(Resource media) {
    String uri = media.getUri();
    String url = "api" + uri + "/" + media.getTitle();
    return url;
  }

  @Inject
  Image image;

  @Override
  public void setValue(Resource value) {
    super.setValue(value);
    if (value != null) {
      String url = getMediaUrl(value);
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
