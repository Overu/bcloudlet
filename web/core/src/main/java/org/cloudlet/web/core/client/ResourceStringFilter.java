package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.Widget;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

import org.cloudlet.web.core.Resource;

public class ResourceStringFilter<T extends Resource> extends StringFilter<T> {

  public ResourceStringFilter(ValueProvider<? super T, String> valueProvider) {
    super(valueProvider);
  }

  public Widget getWiget() {
    return field;
  }
}
