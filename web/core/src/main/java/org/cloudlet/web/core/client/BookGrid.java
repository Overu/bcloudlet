package org.cloudlet.web.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

import org.cloudlet.web.core.Book;
import org.cloudlet.web.core.BookFeed;

import java.util.List;

public class BookGrid extends ResourceGrid<Book, BookFeed> {

  interface BookPorperties extends PropertyAccess<Book> {
    ModelKeyProvider<Book> id();

    ValueProvider<Book, String> title();
  }

  private static BookPorperties properties = GWT.create(BookPorperties.class);

  @Override
  public Class<BookFeed> getResourceType() {
    return BookFeed.class;
  }

  @Override
  protected AbstractSafeHtmlRenderer<Book> getCell() {
    return new AbstractSafeHtmlRenderer<Book>() {

      @Override
      public SafeHtml render(final Book book) {
        return ResourceGrid.r.renderItem(book.getTitle() == null ? "" : book.getTitle(), ResourceGrid.resources.css());
      }
    };
  }

  @Override
  protected ModelKeyProvider<Book> getKey() {
    return properties.id();
  }

  @Override
  protected void initColumn(List<ColumnConfig<Book, ?>> l) {
    l.add(new ColumnConfig<Book, String>(properties.title(), 100, "Title"));
  }

  @Override
  protected void initView() {
    setHeadingHtml("Book Grid");
    super.initView();
  }

  @Override
  protected void refresh() {
    getPlace().load(new AsyncCallback<ResourcePlace<BookFeed>>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final ResourcePlace<BookFeed> result) {
        List<Book> books = result.getResource().getEntries();
        store.replaceAll(books);
      }
    });
  }

}
