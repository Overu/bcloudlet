package org.cloudlet.book.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.cloudlet.core.client.Resource;
import org.cloudlet.core.client.ResourceSearch;
import org.cloudlet.core.server.Content;

import java.util.ArrayList;
import java.util.List;

public class BookSearch extends ResourceSearch {

  @Override
  protected AbstractCell<Resource> getCell(final ResourceStyle style) {
    return new AbstractCell<Resource>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Resource value, SafeHtmlBuilder sb) {
        sb.append(template.render(value.getString(Content.TITLE), style));
      }
    };
  }

  @Override
  protected List<String> getSearchTitle() {
    ArrayList<String> titles = new ArrayList<String>();
    titles.add(Content.TITLE);
    return titles;
  }

}
