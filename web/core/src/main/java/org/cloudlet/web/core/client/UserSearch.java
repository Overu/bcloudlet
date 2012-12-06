package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;

import java.util.ArrayList;
import java.util.List;

public class UserSearch extends ResourceSearch<User, UserFeed> {

  @Override
  public Class<UserFeed> getResourceType() {
    return UserFeed.class;
  }

  @Override
  protected AbstractCell<User> getCell(final org.cloudlet.web.core.client.ResourceSearch.ResourceStyle style) {
    return new AbstractCell<User>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, User value, SafeHtmlBuilder sb) {
        sb.append(template.render(value, style));
      }
    };
  }

  @Override
  protected List<String> getSearchTitle() {
    ArrayList<String> titles = new ArrayList<String>();
    titles.add("name");
    return titles;
  }

}
