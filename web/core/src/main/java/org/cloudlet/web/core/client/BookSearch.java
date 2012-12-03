package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import org.cloudlet.web.core.Book;
import org.cloudlet.web.core.BookFeed;

import java.util.ArrayList;
import java.util.List;

public class BookSearch extends ResourceSearch<Book, BookFeed> {

  interface BookProperties extends PropertyAccess<Book> {
    LabelProvider<Book> title();
  }

  interface UserTemplate extends XTemplates {
    @XTemplate("<div class='{style.searchItem}'>{book.title}</div>")
    SafeHtml render(Book book, ResourceStyle style);
  }

  private static BookProperties bookProperties = GWT.create(BookProperties.class);
  private static UserTemplate template = GWT.create(UserTemplate.class);

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
  protected LabelProvider<Book> getSearchLable() {
    return bookProperties.title();
  }

  @Override
  protected List<String> getSearchTitle() {
    ArrayList<String> titles = new ArrayList<String>();
    titles.add("title");
    return titles;
  }

}
