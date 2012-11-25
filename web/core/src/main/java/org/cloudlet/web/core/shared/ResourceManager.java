package org.cloudlet.web.core.shared;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ResourceManager implements PlaceHistoryMapper {

  @Inject
  PlaceController placeController;

  @Root
  @Inject
  ResourcePlace root;

  public ResourcePlace getPlace(Resource resource) {
    ResourcePlace place = getPlace(resource.getUri());
    place.setResource(resource);
    return place;
  }

  @Override
  public ResourcePlace getPlace(String token) {
    return root.getByUri(token);
  }

  @Override
  public String getToken(Place place) {
    ResourcePlace p = (ResourcePlace) place;
    StringBuilder builder = p.getUriBuilder();
    return builder.toString();
  }

  public ResourcePlace getWhere() {
    return (ResourcePlace) placeController.getWhere();
  }

  public void goTo(Resource resource) {
    ResourcePlace place = getPlace(resource);
    goTo(place);
  }

  public void goTo(ResourcePlace place) {
    placeController.goTo(place);
  }

}
