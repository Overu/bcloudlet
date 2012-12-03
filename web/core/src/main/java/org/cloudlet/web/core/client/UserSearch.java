package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;

import java.util.ArrayList;
import java.util.List;

public class UserSearch extends ResourceSearch<User, UserFeed> {

  interface UserProperties extends PropertyAccess<User> {
    LabelProvider<User> name();
  }

  interface UserTemplate extends XTemplates {
    // @XTemplate("<div class='{style.searchItem}'><h3><span><br />by {user.name}</span>{user.email}</h3>{user.phone}</div>")
    @XTemplate("<div class='{style.searchItem}'>{user.name}</div>")
    SafeHtml render(User user, ResourceStyle style);
  }

  private static UserProperties userProperties = GWT.create(UserProperties.class);
  private static UserTemplate template = GWT.create(UserTemplate.class);

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
  protected LabelProvider<User> getSearchLable() {
    return userProperties.name();
  }

  @Override
  protected List<String> getSearchTitle() {
    ArrayList<String> titles = new ArrayList<String>();
    titles.add("name");
    return titles;
  }

}
