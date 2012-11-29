package org.cloudlet.web.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.Formatter;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public abstract class ResourceGrid<T extends Resource, F extends Feed<T>> extends ContentPanel implements ResourceWidget<F> {

  enum ButtonCar {
    GRID("Grid"), TABLE("Table");

    private String name;

    ButtonCar(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }

  @FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
  interface Renderer extends XTemplates {
    @XTemplate(source = "ResourceGrid.html")
    public SafeHtml renderItem(String name, Style style);
  }

  interface ResourcePorperties<T> extends PropertyAccess<T> {
    ModelKeyProvider<T> id();
  }

  interface Resources extends ClientBundle {
    @Source("ResourceGrid.css")
    Style css();
  }

  static class Shorten implements Formatter<String> {

    private int length;

    public Shorten(final int length) {
      this.length = length;
    }

    @Override
    public String format(final String data) {
      return Format.ellipse(data, length);
    }
  }

  static class ShortenFactory {
    public static Shorten getFormat(final int length) {
      return new Shorten(length);
    }
  }

  interface Style extends CssResource {
    String over();

    String select();

    String thumb();

    String thumbWrap();
  }

  public final static String LIST = "list";

  static Renderer r;
  static Resources resources;

  @Inject
  ResourceManager resourceManager;

  protected ListStore<T> store;
  protected PagingLoader<PagingLoadConfig, PagingLoadResult<T>> loader;

  private boolean initialized = false;
  private VerticalLayoutContainer con;
  private Grid<T> grid;
  private ListView<T, T> listView;
  private T selectedItem;
  private ResourcePlace<F> place;

  static {
    r = GWT.create(Renderer.class);
    resources = GWT.create(Resources.class);
    resources.css().ensureInjected();
  }

  @Override
  public ResourcePlace<F> getPlace() {
    return place;
  }

  @Override
  public void setPlace(ResourcePlace<F> place) {
    this.place = place;
    refresh();
  }

  protected abstract AbstractSafeHtmlRenderer<T> getCell();

  protected abstract void initColumn(List<ColumnConfig<T, ?>> l);

  protected void initView() {
    final Style style = resources.css();

    store = new ListStore<T>(new ModelKeyProvider<T>() {

      @Override
      public String getKey(T item) {
        return item.getId();
      }
    });

    RpcProxy<PagingLoadConfig, PagingLoadResult<T>> proxy = new RpcProxy<PagingLoadConfig, PagingLoadResult<T>>() {
      @Override
      public void load(final PagingLoadConfig loadConfig, final AsyncCallback<PagingLoadResult<T>> callback) {
        final MultivaluedMap<String, String> queryParameters = getPlace().getQueryParameters();
        queryParameters.putSingle("limit", String.valueOf(loadConfig.getLimit()));
        queryParameters.putSingle("start", String.valueOf(loadConfig.getOffset()));
        getPlace().load(new AsyncCallback<ResourcePlace<F>>() {
          @Override
          public void onFailure(final Throwable reason) {
          }

          @Override
          public void onSuccess(final ResourcePlace<F> result) {
            List<T> books = result.getResource().getEntries();
            queryParameters.remove("limit");
            queryParameters.remove("start");
            callback.onSuccess(new PagingLoadResultBean<T>(books, result.getResource().getChildrenCount(), loadConfig.getOffset()));
          }
        });
      }
    };

    loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<T>>(proxy);
    loader.setRemoteSort(true);
    loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, T, PagingLoadResult<T>>(store));

    final PagingToolBar toolBar = new PagingToolBar(5);
    toolBar.getElement().getStyle().setProperty("borderBottom", "none");
    toolBar.bind(loader);

    List<ColumnConfig<T, ?>> l = new ArrayList<ColumnConfig<T, ?>>();
    initColumn(l);
    ColumnModel<T> cm = new ColumnModel<T>(l);

    grid = new Grid<T>(store, cm);
    grid.getView().setForceFit(true);
    grid.setLoadMask(true);
    grid.setBorders(true);
    grid.setLoader(loader);
    grid.getView().setEmptyText("Please hit the load button.");
    grid.getSelectionModel().addSelectionHandler(new SelectionHandler<T>() {

      @Override
      public void onSelection(final SelectionEvent<T> event) {
        selectedItem = event.getSelectedItem();
      }
    });

    ListViewCustomAppearance<T> appearance = new ListViewCustomAppearance<T>("." + style.thumbWrap(), style.over(), style.select()) {

      @Override
      public void renderEnd(final SafeHtmlBuilder builder) {
        String markup = new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append("\"></div>").toString();
        builder.appendHtmlConstant(markup);
      }

      @Override
      public void renderItem(final SafeHtmlBuilder builder, final SafeHtml content) {
        builder.appendHtmlConstant("<div class='" + style.thumbWrap() + "' style='border: 1px solid white'>");
        builder.append(content);
        builder.appendHtmlConstant("</div>");
      }
    };

    listView = new ListView<T, T>(store, new IdentityValueProvider<T>() {
      @Override
      public void setValue(final T object, final T value) {
      }
    }, appearance);
    listView.setCell(new SimpleSafeHtmlCell<T>(getCell()));
    listView.getSelectionModel().addSelectionHandler(new SelectionHandler<T>() {

      @Override
      public void onSelection(final SelectionEvent<T> event) {
        selectedItem = event.getSelectedItem();
      }
    });

    ToggleGroup group = new ToggleGroup();
    ToolBar buttonBar = new ToolBar();
    buttonBar.setPack(BoxLayoutPack.END);
    buttonBar.add(new LabelToolItem("View :&nbsp&nbsp&nbsp"));
    for (ButtonCar car : ButtonCar.values()) {
      final ToggleButton button = new ToggleButton(car.getName());
      button.setWidth(40);
      button.setData("car", car);
      button.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue() == true) {
            selectView(((ButtonCar) button.getData("car")).getName());
          }
        }
      });
      group.add(button);
      buttonBar.add(button);
    }
    ToggleButton gridButton = (ToggleButton) buttonBar.getWidget(1);
    gridButton.setValue(true);

    con = new VerticalLayoutContainer();
    con.add(buttonBar, new VerticalLayoutData(1, -1));
    con.add(toolBar, new VerticalLayoutData(1, -1));
    selectView(((ButtonCar) gridButton.getData("car")).getName());

    setWidget(con);
    setButtonAlign(BoxLayoutPack.CENTER);
    addButton(new TextButton("Add", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        ResourcePlace place = getPlace().getRendition(UserFeedEditor.NEW);
        resourceManager.goTo(place);
      }
    }));
    addButton(new TextButton("Refresh", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        refresh();
      }
    }));
    addButton(new TextButton("Edit", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (selectedItem == null || selectedItem.equals("")) {
          return;
        }
        resourceManager.goTo(selectedItem);
      }
    }));
    addButton(new TextButton("Delete", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (selectedItem == null || selectedItem.equals("")) {
          return;
        }
        resourceManager.getPlace(selectedItem).delete(new AsyncCallback<ResourcePlace<T>>() {
          @Override
          public void onFailure(Throwable caught) {
          }

          @Override
          public void onSuccess(ResourcePlace<T> result) {
            refresh();
          }
        });
      }
    }));
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  protected void refresh() {
    loader.load();
    // getPlace().load(new AsyncCallback<ResourcePlace<F>>() {
    // @Override
    // public void onFailure(final Throwable reason) {
    // }
    //
    // @Override
    // public void onSuccess(final ResourcePlace<F> result) {
    // List<T> books = result.getResource().getEntries();
    // store.replaceAll(books);
    // }
    // });
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }

  private void selectView(final String viewName) {
    boolean cell = viewName.equals("Table");
    if (!cell) {
      con.remove(listView);
      con.add(grid, new VerticalLayoutData(1, 1));
    } else {
      con.remove(grid);
      con.add(listView, new VerticalLayoutData(1, 1));
      listView.setSize("100%", "100%");
    }
    con.onResize();
  }
}
