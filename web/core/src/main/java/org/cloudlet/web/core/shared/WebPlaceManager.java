package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import java.util.logging.Logger;

@Singleton
public class WebPlaceManager implements PlaceHistoryMapper {

  @HomePlace
  @Inject
  WebPlace homePlace;

  @Inject
  PlaceController placeController;

  private static final Logger log = Logger.getLogger(PlaceController.class.getName());

  @Inject
  EventBus eventBus;

  @Override
  public Place getPlace(final String token) {
    WebPlace place = homePlace.findChild(token);
    return place;
  }

  @Override
  public String getToken(final Place place) {
    WebPlace p = (WebPlace) place;
    StringBuilder builder = p.getUriBuilder();
    String viewType = p.getViewType();
    if (viewType != null && viewType.length() > 0) {
      builder.append("#").append(viewType);
    }
    return builder.toString();
  }

  public WebPlace getWhere() {
    return (WebPlace) placeController.getWhere();
  }

  public void goTo(final String uri) {
    WebPlace place = homePlace.findChild(uri);
    if (place != null) {
      goTo(place);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.place.shared.PlaceController#goTo(com.google.gwt.place.shared.Place)
   */
  public void goTo(WebPlace newPlace) {
    WebPlace where = getWhere();
    if (where != null && newPlace.equals(where)
        && !newPlace.getViewType().equals(newPlace.getActiveView())) {
      newPlace.setActiveView(newPlace.getViewType());
      eventBus.fireEvent(new PlaceChangeEvent(newPlace));
    } else {
      placeController.goTo(newPlace);
    }
  }

  public void goTo(final WebPlace place, String view) {
    place.setViewType(view);
    goTo(place);
  }

}
