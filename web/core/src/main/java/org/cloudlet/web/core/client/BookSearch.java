package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import org.cloudlet.web.core.Book;
import org.cloudlet.web.core.BookFeed;

import java.util.ArrayList;
import java.util.List;

public class BookSearch extends ResourceSearch<Book, BookFeed> {

  @Override
  public Class<BookFeed> getResourceType() {
    return BookFeed.class;
  }

  @Override
  protected AbstractCell<Book> getCell(final org.cloudlet.web.core.client.ResourceSearch.ResourceStyle style) {
    return new AbstractCell<Book>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Book value, SafeHtmlBuilder sb) {
        sb.append(template.render(value, style));
      }
    };
  }

  @Override
  protected List<String> getSearchTitle() {
    ArrayList<String> titles = new ArrayList<String>();
    titles.add("title");
    return titles;
  }

}
