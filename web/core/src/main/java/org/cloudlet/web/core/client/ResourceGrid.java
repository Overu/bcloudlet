package org.cloudlet.web.core.client;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.Formatter;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.HeaderContextMenuEvent.HeaderContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public abstract class ResourceGrid extends ContentPanel implements TakesResource {

  enum ColumnItemCar {
    SELECTALL("selectAll"), DESELECTALL("deselectAll");

    private String name;

    ColumnItemCar(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }

  @FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
  interface Renderer extends XTemplates {
    @XTemplate(source = "ResourceGrid.html")
    public SafeHtml renderItem(String name, String imageUrl, Style style);
  }

  interface Resources extends ClientBundle {
    ImageResource add();

    ImageResource cover();

    @Source("ResourceGrid.css")
    Style css();

    ImageResource delete();

    ImageResource edit();

    ImageResource refresh();
  }

  enum SelectButtonCar {
    ADD("Add", resources.add()), REFRESH("Refresh", resources.refresh()), DELETE("Delete", resources.delete()), EDIT("Edit", resources
        .edit());

    private String name;
    private ImageResource image;

    SelectButtonCar(String name, ImageResource image) {
      this.name = name;
      this.image = image;
    }

    ImageResource getImage() {
      return image;
    }

    String getName() {
      return name;
    }
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

  enum ViewButtonCar {
    GRID("Grid"), TABLE("Table");

    private String name;

    ViewButtonCar(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }
  }

  public final static String LIST = "list";

  static Renderer r;
  static Resources resources;

  @Inject
  ResourceManager resourceManager;

  protected PagingLoader<FilterPagingLoadConfig, PagingLoadResult<Resource>> loader;
  private ResourceSearch resourceSearch;
  private VerticalLayoutContainer con;
  private ListView<Resource, Resource> listView;
  private Grid<Resource> grid;
  private List<ColumnConfig<Resource, ?>> l;

  private boolean initialized = false;
  private Resource selectedItem;
  private Resource place;

  static {
    r = GWT.create(Renderer.class);
    resources = GWT.create(Resources.class);
    resources.css().ensureInjected();
  }

  public ResourceSearch getResourceSearch() {
    return resourceSearch;
  }

  @Override
  public Resource getValue() {
    return place;
  }

  public void setResourceSearch(ResourceSearch resourceSearch) {
    this.resourceSearch = resourceSearch;
  }

  @Override
  public void setValue(Resource place) {
    this.place = place;
    if (getResourceSearch() != null) {
      getResourceSearch().setValue(place);
    }
    refresh();
  }

  protected <N> ColumnConfig<Resource, N> columnConfigProvider(ValueProvider<Resource, N> valueProvider, int width, String header) {
    return columnConfigProvider(valueProvider, width, header, null);
  }

  protected <N> ColumnConfig<Resource, N> columnConfigProvider(ValueProvider<Resource, N> valueProvider, int width, String header,
      Cell<N> cell) {
    ColumnConfig<Resource, N> column = new ColumnConfig<Resource, N>(valueProvider, width, header);
    if (cell != null) {
      column.setCell(cell);
    }
    l.add(column);
    return column;
  }

  protected String getCoverUrl(Resource media) {
    if (media == null || media.getTitle().equals("")) {
      return resources.cover().getSafeUri().asString();
    }
    String url = "api" + media.getString("uri") + "/" + media.getString("id");
    return url;
  }

  protected abstract void initColumn();

  protected void initFilter(GridFilters<Resource> filters) {
  }

  protected abstract SafeHtml initListSafeHtml(Resource t);

  protected void initView() {
    final Style style = resources.css();

    ListStore<Resource> store = new ListStore<Resource>(new ModelKeyProvider<Resource>() {

      @Override
      public String getKey(Resource item) {
        return item.getId();
      }
    });

    IdentityValueProvider<Resource> valueProvider = new IdentityValueProvider<Resource>();

    DataProxy<FilterPagingLoadConfig, PagingLoadResult<Resource>> proxy =
        new DataProxy<FilterPagingLoadConfig, PagingLoadResult<Resource>>() {
          @Override
          public void load(final FilterPagingLoadConfig loadConfig, final Callback<PagingLoadResult<Resource>, Throwable> callback) {
            MultivaluedMap<String, String> queryParameters = getValue().getQueryParameters();
            final QueryBuilder builder = QueryBuilder.get(queryParameters);
            builder.filter(loadConfig.getFilters());
            builder.sort(loadConfig.getSortInfo());
            builder.limit(String.valueOf(loadConfig.getLimit()), String.valueOf(loadConfig.getOffset()));
            getValue().load(new AsyncCallback<Resource>() {
              @Override
              public void onFailure(final Throwable reason) {
              }

              @Override
              public void onSuccess(final Resource result) {
                List<Resource> books = result.getList(CorePackage.ENTRIES);
                builder.clear();
                callback.onSuccess(new PagingLoadResultBean<Resource>(books, result.getQueryCount().intValue(), loadConfig.getOffset()));
              }
            });
          }
        };

    loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<Resource>>(proxy) {
      @Override
      protected FilterPagingLoadConfig newLoadConfig() {
        return new FilterPagingLoadConfigBean();
      }
    };
    loader.setRemoteSort(true);
    loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, Resource, PagingLoadResult<Resource>>(store));

    CheckBoxSelectionModel<Resource> sm = new CheckBoxSelectionModel<Resource>(valueProvider);
    ColumnConfig<Resource, Resource> checkColumn = sm.getColumn();
    checkColumn.setMenuDisabled(false);
    checkColumn.setWidth(35);

    l = new ArrayList<ColumnConfig<Resource, ?>>();
    l.add(checkColumn);
    initColumn();
    ColumnModel<Resource> cm = new ColumnModel<Resource>(l);

    grid = new Grid<Resource>(store, cm);
    grid.setSelectionModel(sm);
    grid.getView().setForceFit(true);
    grid.getView().setStripeRows(true);
    grid.getView().setColumnLines(true);
    grid.setLoadMask(true);
    grid.setBorders(true);
    grid.setLoader(loader);
    grid.getView().setEmptyText("Please hit the load button.");
    grid.getSelectionModel().addSelectionHandler(new SelectionHandler<Resource>() {

      @Override
      public void onSelection(final SelectionEvent<Resource> event) {
        selectedItem = event.getSelectedItem();
      }
    });
    grid.addHeaderContextMenuHandler(new HeaderContextMenuHandler() {
      @Override
      public void onHeaderContextMenu(HeaderContextMenuEvent event) {
        int columnIndex = event.getColumnIndex();
        if (columnIndex != 0) {
          return;
        }
        final CheckBoxSelectionModel<Resource> selectionModel = (CheckBoxSelectionModel<Resource>) grid.getSelectionModel();
        Menu menu = event.getMenu();
        for (final ColumnItemCar columnItem : ColumnItemCar.values()) {
          MenuItem item = new MenuItem(columnItem.getName());
          item.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
              switch (columnItem) {
              case DESELECTALL:
                selectionModel.setSelectAllChecked(false);
                break;
              case SELECTALL:
                selectionModel.setSelectAllChecked(true);
                break;
              default:
                break;
              }
            }
          });
          menu.add(item);
        }
      }
    });

    GridFilters<Resource> filters = new GridFilters<Resource>(loader);
    filters.initPlugin(grid);
    initFilter(filters);

    ListViewCustomAppearance<Resource> appearance =
        new ListViewCustomAppearance<Resource>("." + style.thumbWrap(), style.over(), style.select()) {

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

    listView = new ListView<Resource, Resource>(store, valueProvider, appearance);
    listView.setCell(new SimpleSafeHtmlCell<Resource>(new AbstractSafeHtmlRenderer<Resource>() {

      @Override
      public SafeHtml render(Resource object) {
        return initListSafeHtml(object);
      }
    }));
    listView.getSelectionModel().addSelectionHandler(new SelectionHandler<Resource>() {

      @Override
      public void onSelection(final SelectionEvent<Resource> event) {
        selectedItem = event.getSelectedItem();
      }
    });

    final ButtonBar buttonBar = new ButtonBar();
    buttonBar.addStyleName("x-toolbar-mark");
    buttonBar.setMinButtonWidth(75);
    buttonBar.setPack(BoxLayoutPack.START);

    for (final SelectButtonCar car : SelectButtonCar.values()) {
      final TextButton button = new TextButton(car.getName(), car.getImage());
      button.addSelectHandler(new SelectHandler() {

        @Override
        public void onSelect(SelectEvent event) {
          selectBase(car);
        }
      });
      buttonBar.add(button);
    }

    final PagingToolBar toolBar = new PagingToolBar(5);
    toolBar.getElement().getStyle().setProperty("borderBottom", "none");
    toolBar.bind(loader);

    ToggleGroup viewGroup = new ToggleGroup();
    ToolBar viewBar = new ToolBar();
    viewBar.setPack(BoxLayoutPack.END);
    viewBar.setBorders(false);
    viewBar.add(new LabelToolItem("View :&nbsp&nbsp&nbsp"));
    for (final ViewButtonCar car : ViewButtonCar.values()) {
      final ToggleButton button = new ToggleButton(car.getName());
      button.setWidth(40);
      button.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue() == true) {
            selectView(car);
          }
        }
      });
      viewGroup.add(button);
      viewBar.add(button);
    }
    ToggleButton gridButton = (ToggleButton) viewBar.getWidget(1);
    gridButton.setValue(true);

    HorizontalLayoutContainer hor1 = new HorizontalLayoutContainer();
    hor1.add(toolBar, new HorizontalLayoutData(0.85, 1));
    hor1.add(viewBar, new HorizontalLayoutData(0.15, 1));

    HorizontalLayoutContainer hor2 = new HorizontalLayoutContainer();
    hor2.add(buttonBar, new HorizontalLayoutData(getResourceSearch() == null ? 1 : 0.4, 1));
    if (getResourceSearch() != null) {
      getResourceSearch().setPack(BoxLayoutPack.END);
      hor2.add(getResourceSearch(), new HorizontalLayoutData(0.6, 1));
    }

    con = new VerticalLayoutContainer();
    con.add(hor2, new VerticalLayoutData(1, 34));
    con.add(hor1, new VerticalLayoutData(1, 27));
    selectView(ViewButtonCar.GRID);

    setWidget(con);
    setBodyBorder(false);
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  protected void refresh() {
    grid.getView().getBody().setId("resourcegrid");
    loader.load();
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }

  private void selectBase(SelectButtonCar car) {
    switch (car) {
    case ADD:
      Resource place = getValue().getRendition(UserFeedEditor.NEW);
      resourceManager.goTo(place);
      break;
    case REFRESH:
      refresh();
      break;
    case DELETE:
      if (selectedItem == null || selectedItem.equals("")) {
        return;
      }
      selectedItem.delete(new AsyncCallback<Resource>() {
        @Override
        public void onFailure(Throwable caught) {
        }

        @Override
        public void onSuccess(Resource result) {
          refresh();
        }
      });
      break;
    case EDIT:
      if (selectedItem == null) {
        return;
      }
      resourceManager.goTo(selectedItem);
      break;
    default:
      break;
    }
  }

  private void selectView(ViewButtonCar car) {
    switch (car) {
    case TABLE:
      con.remove(grid);
      con.add(listView, new VerticalLayoutData(1, 1));
      listView.setSize("100%", "100%");
      break;
    case GRID:
      con.remove(listView);
      con.add(grid, new VerticalLayoutData(1, 1));
      break;
    default:
      break;
    }
    con.onResize();
  }
}
