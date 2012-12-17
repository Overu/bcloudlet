package org.cloudlet.web.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;

import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;

public class UserGrid extends ResourceGrid<User, UserFeed> {

  interface UserPorperties extends PropertyAccess<User> {
    ValueProvider<User, String> email();

    ValueProvider<User, String> name();

    ValueProvider<User, String> phone();

    ValueProvider<User, String> state();

    ValueProvider<User, String> zip();
  }

  @Inject
  UserSearch userSearch;

  private static UserPorperties properties = GWT.create(UserPorperties.class);

  @Override
  public Class<UserFeed> getResourceType() {
    return UserFeed.class;
  }

  @Override
  protected void initColumn() {
    columnConfigProvider(properties.name(), 100, "Sender");
    columnConfigProvider(properties.email(), 165, "Email");
    columnConfigProvider(properties.phone(), 100, "Phone");
    columnConfigProvider(properties.state(), 50, "State");
    columnConfigProvider(properties.zip(), 65, "Zip Code");
  }

  @Override
  protected void initFilter(GridFilters<User> filters) {
    filters.addFilter(new ResourceStringFilter<User>(properties.name()));
  }

  @Override
  protected SafeHtml initListSafeHtml(User t) {
    return ResourceGrid.r.renderItem(t.getName(), getCoverUrl(null), ResourceGrid.resources.css());
  }

  @Override
  protected void initView() {
    setHeadingText("User Grid");
    setResourceSearch(userSearch);
    super.initView();
  }
}
