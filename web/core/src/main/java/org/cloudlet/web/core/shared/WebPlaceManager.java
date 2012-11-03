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
  WebPlace homePlace;

  @Inject
  PlaceController placeController;

  @Override
  public Place getPlace(final String token) {
    WebPlace place = homePlace.findChild(token);
    return place;
  }

  @Override
  public String getToken(final Place place) {
    WebPlace p = (WebPlace) place;
    StringBuilder builder = p.getUriBuilder();
    return builder.toString();
  }

  public WebPlace getWhere() {
    return (WebPlace) placeController.getWhere();
  }

  public void goTo(final String uri) {
    goTo(homePlace, uri);
  }

  public void goTo(WebPlace newPlace) {
    placeController.goTo(newPlace);
  }

  public void goTo(final WebPlace place, String uri) {
    WebPlace newPlace = place.isFolder() ? place.findChild(uri) : place.getParent().findChild(uri);
    if (newPlace != null) {
      goTo(newPlace);
    }
  }
}
