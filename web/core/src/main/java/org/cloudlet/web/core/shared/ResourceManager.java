package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ResourceManager implements PlaceHistoryMapper {

  @Root
  @Inject
  Entry root;

  @Inject
  PlaceController placeController;

  @Override
  public Resource getPlace(String token) {
    Resource place = root.findChild(token);
    return place;
  }

  @Override
  public String getToken(Place place) {
    Resource p = (Resource) place;
    StringBuilder builder = p.getUriBuilder();
    return builder.toString();
  }

  public Resource getWhere() {
    return (Resource) placeController.getWhere();
  }

  public void goTo(Content place, String uri) {
    Resource newPlace = place.findChild(uri);
    if (newPlace != null) {
      goTo(newPlace);
    }
  }

  public void goTo(Resource newPlace) {
    placeController.goTo(newPlace);
  }

  public void goTo(String uri) {
    goTo(root, uri);
  }
}
