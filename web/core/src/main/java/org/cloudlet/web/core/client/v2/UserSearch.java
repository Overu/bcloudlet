package org.cloudlet.web.core.client.v2;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.util.ArrayList;
import java.util.List;

public class UserSearch extends ResourceSearch {

  @Override
  protected AbstractCell<Resource> getCell(final ResourceStyle style) {
    return new AbstractCell<Resource>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Resource value, SafeHtmlBuilder sb) {
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
