package org.cloudlet.web.core.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.cloudlet.web.core.Resource;

@Singleton
public class ResourceManager implements PlaceHistoryMapper {

  @Inject
  PlaceController placeController;

  @RootPlace
  @Inject
  ResourcePlace root;

  @Override
  public ResourcePlace getPlace(String token) {
    return root.getByUri(token);
  }

  public <T extends Resource> ResourcePlace<T> getPlace(T resource) {
    ResourcePlace<T> place = getPlace(resource.getUri());
    place.setResource(resource);
    return place;
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
