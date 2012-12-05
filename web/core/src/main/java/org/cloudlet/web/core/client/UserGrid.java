package org.cloudlet.web.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;

import java.util.List;

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
  protected AbstractSafeHtmlRenderer<User> getCell() {
    return new AbstractSafeHtmlRenderer<User>() {

      @Override
      public SafeHtml render(final User user) {
        return ResourceGrid.r.renderItem(user.getName(), getCoverUrl(null), ResourceGrid.resources.css());
      }
    };
  }

  @Override
  protected void initColumn(List<ColumnConfig<User, ?>> l) {
    l.add(new ColumnConfig<User, String>(properties.name(), 100, "Sender"));
    l.add(new ColumnConfig<User, String>(properties.email(), 165, "Email"));
    l.add(new ColumnConfig<User, String>(properties.phone(), 100, "Phone"));
    l.add(new ColumnConfig<User, String>(properties.state(), 50, "State"));
    l.add(new ColumnConfig<User, String>(properties.zip(), 65, "Zip Code"));
  }

  @Override
  protected ResourceSearch<User, UserFeed> initSearch() {
    return userSearch;
  }

  @Override
  protected void initView() {
    setHeadingText("User Grid");
    super.initView();
  }
}
