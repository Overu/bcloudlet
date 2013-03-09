package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import org.cloudlet.web.core.server.Content;
import org.cloudlet.web.core.server.Feed;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public abstract class ResourceSearch extends SimpleContainer implements TakesResource {

  interface Bundle extends ClientBundle {
    @Source("ResourceComboBox.css")
    ResourceStyle css();
  }

  interface ResourceStyle extends CssResource {
    String searchItem();
  }

  interface Template extends XTemplates {
    @XTemplate("<div class='{style.searchItem}'>{resource}</div>")
    SafeHtml render(String resource, ResourceStyle style);
  }

  protected static Template template = GWT.create(Template.class);

  @Inject
  ResourceManager resourceManager;

  private boolean initialized = false;
  private Resource place;
  private ComboBox<Resource> combo;
  private ToolBar toolBar = new ToolBar();

  @Override
  public Resource getValue() {
    return place;
  }

  public void setPack(BoxLayoutPack pack) {
    toolBar.setPack(pack);
  }

  @Override
  public void setValue(Resource resource) {
    this.place = resource;
  }

  protected abstract AbstractCell<Resource> getCell(ResourceStyle style);

  protected abstract List<String> getSearchTitle();

  protected void initView() {
    ListStore<Resource> store = new ListStore<Resource>(new ModelKeyProvider<Resource>() {
      @Override
      public String getKey(Resource item) {
        return item.getId();
      }
    });

    DataProxy<PagingLoadConfig, PagingLoadResult<Resource>> proxy = new DataProxy<PagingLoadConfig, PagingLoadResult<Resource>>() {

      @Override
      public void load(final PagingLoadConfig loadConfig, final Callback<PagingLoadResult<Resource>, Throwable> callback) {
        MultivaluedMap<String, String> queryParameters = getValue().getQueryParameters();
        final QueryBuilder builder = QueryBuilder.get(queryParameters);
        for (String title : getSearchTitle()) {
          String text = combo.getText();
          if (text == null && text.equals("")) {
            continue;
          }
          builder.buildQuery(Content.SEARCH, title, text);
        }
        builder.limit(String.valueOf(loadConfig.getLimit()), String.valueOf(loadConfig.getOffset()));
        getValue().load(new AsyncCallback<Resource>() {
          @Override
          public void onFailure(final Throwable reason) {
          }

          @Override
          public void onSuccess(final Resource result) {
            List<Resource> books = result.getList(Feed.ENTRIES);
            builder.clear();
            callback.onSuccess(new PagingLoadResultBean<Resource>(books, result.getQueryCount().intValue(), loadConfig.getOffset()));
          }
        });
      }
    };
    PagingLoader<PagingLoadConfig, PagingLoadResult<Resource>> loader =
        new PagingLoader<PagingLoadConfig, PagingLoadResult<Resource>>(proxy);
    loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, Resource, PagingLoadResult<Resource>>(store));

    final Bundle b = GWT.create(Bundle.class);
    b.css().ensureInjected();

    ListView<Resource, Resource> view = new ListView<Resource, Resource>(store, new IdentityValueProvider<Resource>(), getCell(b.css()));

    ComboBoxCell<Resource> cell = new ComboBoxCell<Resource>(store, new LabelProvider<Resource>() {

      @Override
      public String getLabel(Resource item) {
        return item.toString();
      }
    }, view);
    combo = new ComboBox<Resource>(cell);
    combo.setLoader(loader);
    combo.setWidth(400);
    combo.setHideTrigger(true);
    combo.setPageSize(5);
    combo.setMinChars(1);
    combo.addBeforeSelectionHandler(new BeforeSelectionHandler<Resource>() {

      @Override
      public void onBeforeSelection(BeforeSelectionEvent<Resource> event) {
        event.cancel();
        Resource t = combo.getListView().getSelectionModel().getSelectedItem();
        if (t == null) {
          return;
        }
        resourceManager.goTo(t);
      }
    });

    toolBar.add(new LabelToolItem("Search:"));
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
