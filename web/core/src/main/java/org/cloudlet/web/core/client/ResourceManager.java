package org.cloudlet.web.core.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.cloudlet.web.core.Root;

@Singleton
public class ResourceManager implements PlaceHistoryMapper {

  @Inject
  PlaceController placeController;

  @Root
  @Inject
  Resource root;

  @Override
  public Resource getPlace(String token) {
    return root.getByUri(token);
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

  public void goTo(Resource place) {
    placeController.goTo(place);
  }

}
