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

import org.cloudlet.web.core.Book;
import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Resource;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.service.FeedBean;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public abstract class ResourceSearch<T extends Resource, F extends Feed<T>> extends SimpleContainer implements ResourceWidget<F> {

  interface Bundle extends ClientBundle {
    @Source("ResourceComboBox.css")
    ResourceStyle css();
  }

  interface ResourceStyle extends CssResource {
    String searchItem();
  }

  interface Template extends XTemplates {
    @XTemplate("<div class='{style.searchItem}'>{book.title}</div>")
    SafeHtml render(Book book, ResourceStyle style);

    @XTemplate("<div class='{style.searchItem}'>{user.name}</div>")
    SafeHtml render(User user, ResourceStyle style);
  }

  protected static Template template = GWT.create(Template.class);

  @Inject
  ResourceManager resourceManager;

  private boolean initialized = false;
  private ResourcePlace<F> place;
  private ComboBox<T> combo;
  private ToolBar toolBar = new ToolBar();

  @Override
  public ResourcePlace<F> getPlace() {
    return place;
  }

  public void setPack(BoxLayoutPack pack) {
    toolBar.setPack(pack);
  }

  @Override
  public void setPlace(ResourcePlace<F> resource) {
    this.place = resource;
  }

  protected abstract AbstractCell<T> getCell(ResourceStyle style);

  protected abstract List<String> getSearchTitle();

  protected void initView() {
    ListStore<T> store = new ListStore<T>(new ModelKeyProvider<T>() {
      @Override
      public String getKey(T item) {
        return item.getId();
      }
    });

    DataProxy<PagingLoadConfig, PagingLoadResult<T>> proxy = new DataProxy<PagingLoadConfig, PagingLoadResult<T>>() {

      @Override
      public void load(final PagingLoadConfig loadConfig, final Callback<PagingLoadResult<T>, Throwable> callback) {
        MultivaluedMap<String, String> queryParameters = getPlace().getQueryParameters();
        final QueryBuilder builder = QueryBuilder.get(queryParameters);
        List<String> titles = getSearchTitle();
        if (titles != null && titles.size() > 0) {
          for (String title : titles) {
            String text = combo.getText();
            if (text == null && text.equals("")) {
              continue;
            }
            builder.buildQuery(FeedBean.SEARCH, title, text);
          }
        }
        builder.limit(String.valueOf(loadConfig.getLimit()), String.valueOf(loadConfig.getOffset()));
        getPlace().load(new AsyncCallback<ResourcePlace<F>>() {
          @Override
          public void onFailure(final Throwable reason) {
          }

          @Override
          public void onSuccess(final ResourcePlace<F> result) {
            List<T> books = result.getResource().getEntries();
            builder.clear();
            callback.onSuccess(new PagingLoadResultBean<T>(books, result.getResource().getQueryCount().intValue(), loadConfig.getOffset()));
          }
        });
      }
    };
    PagingLoader<PagingLoadConfig, PagingLoadResult<T>> loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<T>>(proxy);
    loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, T, PagingLoadResult<T>>(store));

    final Bundle b = GWT.create(Bundle.class);
    b.css().ensureInjected();

    ListView<T, T> view = new ListView<T, T>(store, new IdentityValueProvider<T>(), getCell(b.css()));

    ComboBoxCell<T> cell = new ComboBoxCell<T>(store, new LabelProvider<T>() {

      @Override
      public String getLabel(T item) {
        return item.toString();
      }
    }, view);
    combo = new ComboBox<T>(cell);
    combo.setLoader(loader);
    combo.setWidth(400);
    combo.setHideTrigger(true);
    combo.setPageSize(5);
    combo.addBeforeSelectionHandler(new BeforeSelectionHandler<T>() {

      @Override
      public void onBeforeSelection(BeforeSelectionEvent<T> event) {
        event.cancel();
        T t = combo.getListView().getSelectionModel().getSelectedItem();
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
