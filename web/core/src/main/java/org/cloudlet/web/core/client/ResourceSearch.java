package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent;
import com.sencha.gxt.data.shared.loader.BeforeLoadEvent.BeforeLoadHandler;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Resource;

public abstract class ResourceSearch<T extends Resource, F extends Feed<T>> extends SimpleContainer implements ResourceWidget<F> {

  interface Bundle extends ClientBundle {
    @Source("ResourceComboBox.css")
    ResourceStyle css();
  }

  interface ResourceStyle extends CssResource {
    String searchItem();
  }

  private boolean initialized = false;
  private ResourcePlace<F> place;
  private ComboBox<T> combo;

  @Override
  public ResourcePlace<F> getPlace() {
    return place;
  }

  @Override
  public void setPlace(ResourcePlace<F> resource) {
    this.place = resource;
  }

  protected abstract AbstractCell<T> getCell(ResourceStyle style);

  protected abstract LabelProvider<T> getSearchLable();

  protected void initView() {
    ListStore<T> store = new ListStore<T>(new ModelKeyProvider<T>() {

      @Override
      public String getKey(T item) {
        return item.getId();
      }
    });

    DataProxy<PagingLoadConfig, PagingLoadResult<T>> proxy = new DataProxy<PagingLoadConfig, PagingLoadResult<T>>() {

      @Override
      public void load(PagingLoadConfig loadConfig, Callback<PagingLoadResult<T>, Throwable> callback) {
      }
    };
    PagingLoader<PagingLoadConfig, PagingLoadResult<T>> loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<T>>(proxy);
    loader.addBeforeLoadHandler(new BeforeLoadHandler<PagingLoadConfig>() {
      @Override
      public void onBeforeLoad(BeforeLoadEvent<PagingLoadConfig> event) {

      }
    });

    final Bundle b = GWT.create(Bundle.class);
    b.css().ensureInjected();

    ListView<T, T> view = new ListView<T, T>(store, new IdentityValueProvider<T>());
    view.setCell(getCell(b.css()));

    ComboBoxCell<T> cell = new ComboBoxCell<T>(store, getSearchLable(), view);
    combo = new ComboBox<T>(cell);
    combo.setLoader(loader);
    combo.setWidth(580);
    combo.setHideTrigger(true);
    combo.setPageSize(10);
    combo.addBeforeSelectionHandler(new BeforeSelectionHandler<T>() {

      @Override
      public void onBeforeSelection(BeforeSelectionEvent<T> event) {
        event.cancel();
        T f = combo.getListView().getSelectionModel().getSelectedItem();
      }
    });

    ToolBar toolBar = new ToolBar();
    toolBar.add(new LabelToolItem("Serach："));
    toolBar.add(combo);
    setWidget(toolBar);
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }
}
