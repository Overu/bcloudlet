package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.inject.Inject;

import org.cloudlet.web.core.shared.CorePackage;

public class BookGrid extends ResourceGrid {

  @Inject
  BookSearch bookSearch;

  @Override
  protected void initColumn() {
    columnConfigProvider(new ResourceValueProvider(CorePackage.COVER), 8, "Cover", new AbstractCell<Resource>() {
      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Resource value, SafeHtmlBuilder sb) {
        StringBuffer imageUrl = new StringBuffer();
        imageUrl.append("<div style='text-align: center;'><img style='width: 65px;height: 65px;' src='").append(getCoverUrl(value)).append(
            "'></div>");
        sb.append(SafeHtmlUtils.fromSafeConstant(imageUrl.toString()));
      }
    });
    columnConfigProvider(new StringValueProvider(CorePackage.TITLE), 100, "Title");
  }

  @Override
  protected SafeHtml initListSafeHtml(Resource t) {
    return ResourceGrid.r.renderItem(t.getTitle(), getCoverUrl(t.getResource(CorePackage.COVER)), ResourceGrid.resources.css());
  }

  @Override
  protected void initView() {
    setHeadingHtml("Book Grid");
    setResourceSearch(bookSearch);
    super.initView();
  }

}
