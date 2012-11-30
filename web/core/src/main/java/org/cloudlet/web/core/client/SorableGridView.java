package org.cloudlet.web.core.client;

import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.widget.core.client.grid.GridView;

import org.cloudlet.web.core.Resource;

public class SorableGridView<M extends Resource> extends GridView<M> {

  public void sort(int colIndex, SortDir sortDir) {
    doSort(colIndex, sortDir);
  }
}
