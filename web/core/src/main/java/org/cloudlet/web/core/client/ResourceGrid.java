package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
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
import com.sencha.gxt.core.client.ToStringValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.Formatter;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Resource;
import org.cloudlet.web.core.service.FeedBean;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public abstract class ResourceGrid<T extends Resource, F extends Feed<T>> extends ContentPanel implements ResourceWidget<F> {

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

  enum SelectButtonCar {
    ADD("Add"), REFRESH("Refresh"), DELETE("Delete"), EDIT("Edit");

    private String name;

    SelectButtonCar(String name) {
      this.name = name;
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
  public final static String START = "start";
  public final static String LIMIT = "limit";

  static Renderer r;
  static Resources resources;

  @Inject
  ResourceManager resourceManager;

  protected PagingLoader<PagingLoadConfig, PagingLoadResult<T>> loader;

  private boolean initialized = false;
  private VerticalLayoutContainer con;
  private Grid<T> grid;
  private ListView<T, T> listView;
  private T selectedItem;
  private ResourcePlace<F> place;
  private ResourceSearch<T, F> resourceSearch;

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
    if (resourceSearch != null) {
      resourceSearch.setPlace(place);
    }
    refresh();
  }

  protected abstract AbstractSafeHtmlRenderer<T> getCell();

  protected abstract void initColumn(List<ColumnConfig<T, ?>> l);

  protected abstract ResourceSearch<T, F> initSearch();

  protected void initView() {
    final Style style = resources.css();

    ListStore<T> store = new ListStore<T>(new ModelKeyProvider<T>() {

      @Override
      public String getKey(T item) {
        return item.getId();
      }
    });

    IdentityValueProvider<T> valueProvider = new IdentityValueProvider<T>();
    ToStringValueProvider<T> stringProvider = new ToStringValueProvider<T>();

    DataProxy<PagingLoadConfig, PagingLoadResult<T>> proxy = new DataProxy<PagingLoadConfig, PagingLoadResult<T>>() {
      @Override
      public void load(final PagingLoadConfig loadConfig, final Callback<PagingLoadResult<T>, Throwable> callback) {
        final MultivaluedMap<String, String> queryParameters = getPlace().getQueryParameters();
        List<? extends SortInfo> sorts = loadConfig.getSortInfo();
        if (sorts.size() > 0) {
          StringBuilder sb = new StringBuilder();
          for (SortInfo sort : sorts) {
            sb.append(sort.getSortField()).append("|").append(sort.getSortDir().name());
            queryParameters.add(FeedBean.SORT, sb.toString());
          }
        }
        queryParameters.putSingle(LIMIT, String.valueOf(loadConfig.getLimit()));
        queryParameters.putSingle(START, String.valueOf(loadConfig.getOffset()));
        getPlace().load(new AsyncCallback<ResourcePlace<F>>() {
          @Override
          public void onFailure(final Throwable reason) {
          }

          @Override
          public void onSuccess(final ResourcePlace<F> result) {
            List<T> books = result.getResource().getEntries();
            queryParameters.remove(LIMIT);
            queryParameters.remove(START);
            queryParameters.remove(FeedBean.SORT);
            callback.onSuccess(new PagingLoadResultBean<T>(books, result.getResource().getChildrenCount(), loadConfig.getOffset()));
          }
        });
      }
    };

    loader = new PagingLoader<PagingLoadConfig, PagingLoadResult<T>>(proxy);
    loader.setRemoteSort(true);
    loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, T, PagingLoadResult<T>>(store));

    CheckBoxSelectionModel<T> sm = new CheckBoxSelectionModel<T>(valueProvider);
    ColumnConfig<T, ?> column = sm.getColumn();

    List<ColumnConfig<T, ?>> l = new ArrayList<ColumnConfig<T, ?>>();
    l.add(sm.getColumn());
    initColumn(l);
    ColumnModel<T> cm = new ColumnModel<T>(l);

    grid = new Grid<T>(store, cm);
    grid.setSelectionModel(sm);
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

    listView = new ListView<T, T>(store, valueProvider, appearance);
    listView.setCell(new SimpleSafeHtmlCell<T>(getCell()));
    listView.getSelectionModel().addSelectionHandler(new SelectionHandler<T>() {

      @Override
      public void onSelection(final SelectionEvent<T> event) {
        selectedItem = event.getSelectedItem();
      }
    });

    ToggleGroup buttonGroup = new ToggleGroup();
    final ButtonBar buttonBar = new ButtonBar();
    buttonBar.addStyleName("x-toolbar-mark");
    buttonBar.setMinButtonWidth(75);
    buttonBar.setPack(BoxLayoutPack.START);

    for (final SelectButtonCar car : SelectButtonCar.values()) {
      final ToggleButton button = new ToggleButton(car.getName());
      button.addSelectHandler(new SelectHandler() {

        @Override
        public void onSelect(SelectEvent event) {
          selectBase(car);
        }
      });
      buttonGroup.add(button);
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
    resourceSearch = initSearch();
    hor2.add(buttonBar, new HorizontalLayoutData(resourceSearch == null ? 1 : 0.5, 1));
    if (resourceSearch != null) {
      resourceSearch.setPack(BoxLayoutPack.END);
      hor2.add(resourceSearch, new HorizontalLayoutData(0.5, 1));
    }

    con = new VerticalLayoutContainer();
    con.add(hor2, new VerticalLayoutData(1, 34));
    con.add(hor1, new VerticalLayoutData(1, 27));
    selectView(ViewButtonCar.GRID);

    setWidget(con);
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  protected void refresh() {
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
        ResourcePlace place = getPlace().getRendition(UserFeedEditor.NEW);
        resourceManager.goTo(place);
        break;
      case REFRESH:
        refresh();
        break;
      case DELETE:
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
