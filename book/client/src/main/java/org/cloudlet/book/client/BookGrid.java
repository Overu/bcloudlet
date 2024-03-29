package org.cloudlet.book.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.inject.Inject;

import org.cloudlet.book.server.Book;
import org.cloudlet.core.client.IntegerValueProvider;
import org.cloudlet.core.client.Resource;
import org.cloudlet.core.client.ResourceGrid;
import org.cloudlet.core.client.ResourceValueProvider;
import org.cloudlet.core.client.StringValueProvider;
import org.cloudlet.core.server.Content;

public class BookGrid extends ResourceGrid {

  @Inject
  BookSearch bookSearch;

  @Override
  protected void initColumn() {
    columnConfigProvider(new ResourceValueProvider(Book.COVER), 40, "封面", new AbstractCell<Resource>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Resource value, SafeHtmlBuilder sb) {
        StringBuffer imageUrl = new StringBuffer();
        imageUrl.append("<div style='text-align: center;'><img style='width: 65px;height: 65px;' src='").append(getCoverUrl(value)).append(
            "'></div>");
        sb.append(SafeHtmlUtils.fromSafeConstant(imageUrl.toString()));
      }
    });
    columnConfigProvider(new StringValueProvider(Content.TITLE), 200, "书名");
    columnConfigProvider(new StringValueProvider(Book.AUTHOR), 100, "作者");
    columnConfigProvider(new IntegerValueProvider(Book.PRICE), 30, "价格");
    columnConfigProvider(new StringValueProvider(Book.PROMOTED), 20, "促销");
    columnConfigProvider(new StringValueProvider(Book.TAG + "." + Content.TITLE), 30, "分类");
    columnConfigProvider(new StringValueProvider(Book.DATE_PUBLISHED), 30, "出版日期");
  }

  @Override
  protected SafeHtml initListSafeHtml(Resource t) {
    return ResourceGrid.r.renderItem(t.getTitle(), getCoverUrl(t.getResource(Book.COVER)), ResourceGrid.resources.css());
  }

  @Override
  protected void initView() {
    setHeadingHtml("Book Grid");
    setResourceSearch(bookSearch);
    super.initView();
  }

}
