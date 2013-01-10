package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.Widget;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

public class ResourceStringFilter extends StringFilter<Resource> {

  public ResourceStringFilter(ValueProvider<Resource, String> valueProvider) {
    super(valueProvider);
  }

  public Widget getWiget() {
    return field;
  }
}
