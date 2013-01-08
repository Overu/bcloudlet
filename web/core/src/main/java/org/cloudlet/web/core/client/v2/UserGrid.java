package org.cloudlet.web.core.client.v2;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;

public class UserGrid extends ResourceGrid {

  @Inject
  UserSearch userSearch;

  @Override
  protected void initColumn() {
    columnConfigProvider(new StringValueProvider("name"), 100, "Sender");
    columnConfigProvider(new StringValueProvider("email"), 165, "Email");
    columnConfigProvider(new StringValueProvider("phone"), 100, "Phone");
    columnConfigProvider(new StringValueProvider("state"), 50, "State");
    columnConfigProvider(new StringValueProvider("name"), 65, "Zip Code");
  }

  @Override
  protected void initFilter(GridFilters<Resource> filters) {
    filters.addFilter(new ResourceStringFilter(new StringValueProvider("name")));
  }

  @Override
  protected SafeHtml initListSafeHtml(Resource t) {
    return ResourceGrid.r.renderItem(t.getString("name"), getCoverUrl(null), ResourceGrid.resources.css());
  }

  @Override
  protected void initView() {
    setHeadingText("User Grid");
    setResourceSearch(userSearch);
    super.initView();
  }
}
