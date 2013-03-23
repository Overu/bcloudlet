package org.cloudlet.web.core.client;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;

import org.cloudlet.web.core.server.User;

public class UserGrid extends ResourceGrid {

  @Inject
  UserSearch userSearch;

  @Override
  protected void initColumn() {
    columnConfigProvider(new StringValueProvider(User.NAME), 100, "用户名");
    columnConfigProvider(new StringValueProvider(User.EMAIL), 165, "郵箱");
    columnConfigProvider(new StringValueProvider(User.PHONE), 100, "电话");
    columnConfigProvider(new StringValueProvider(User.STATE), 50, "地区");
    columnConfigProvider(new StringValueProvider(User.ZIP), 65, "郵編");
  }

  @Override
  protected void initFilter(GridFilters<Resource> filters) {
    filters.addFilter(new ResourceStringFilter(new StringValueProvider(User.NAME)));
  }

  @Override
  protected SafeHtml initListSafeHtml(Resource t) {
    return ResourceGrid.r.renderItem(t.getString(User.NAME), getCoverUrl(null), ResourceGrid.resources.css());
  }

  @Override
  protected void initView() {
    setHeadingText("User Grid");
    setResourceSearch(userSearch);
    super.initView();
  }
}
