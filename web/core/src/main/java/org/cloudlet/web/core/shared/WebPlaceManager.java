package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class WebPlaceManager implements PlaceHistoryMapper {

  @HomePlace
  @Inject
  Content root;

  @Inject
  PlaceController placeController;

  @Override
  public Content getPlace(String token) {
    Content place = root.findChild(token);
    return place;
  }

  @Override
  public String getToken(Place place) {
    Content p = (Content) place;
    StringBuilder builder = p.getUriBuilder();
    return builder.toString();
  }

  public Content getWhere() {
    return (Content) placeController.getWhere();
  }

  public void goTo(Content newPlace) {
    placeController.goTo(newPlace);
  }

  public void goTo(Content place, String uri) {
    Content newPlace =
        (place instanceof View) ? place.getParent().findChild(uri) : place.findChild(uri);
    if (newPlace != null) {
      goTo(newPlace);
    }
  }

  public void goTo(String uri) {
    goTo(root, uri);
  }
}
