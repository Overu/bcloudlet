package org.cloudlet.web.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.Formatter;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadExceptionEvent;
import com.sencha.gxt.data.shared.loader.LoadExceptionEvent.LoadExceptionHandler;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.IsResource;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserGrid extends WebView implements IsWidget, EntryPoint, ListLoadConfig, IsResource {

  class JSONFeedReader implements DataReader<ListLoadResult<User>, String> {

    @Override
    public ListLoadResult<User> read(final Object loadConfig, final String data) {
      UserFeed parent = (UserFeed) ((IsResource) loadConfig).asResource().getParent();
      JSONObject root = JSONParser.parseLenient(data).isObject();
      JSONObject feed = root.get("dataGraph").isObject().get("root").isObject();
      JSONValue entries = feed.get(Feed.ENTRIES);
      JSONArray records;
      List<User> users = new ArrayList<User>();
      if (entries != null && (records = entries.isArray()) != null) {
        for (int i = 0; i < records.size(); i++) {
          JSONObject json = records.get(i).isObject();
          User user = (User) CoreClientModule.readResource(json);
          user.setParent(parent);
          users.add(user);
        }
      }
      ListLoadResultBean<User> result = new ListLoadResultBean<User>(users);
      return result;
    }
  }

  class JSONStringValueProvider implements ValueProvider<User, String> {

    public String path;

    JSONStringValueProvider(final String path) {
      this.path = path;
    }

    @Override
    public String getPath() {
      return path;
    };

    @Override
    public String getValue(final User user) {
      JSONObject object = user.getNativeData();
      JSONValue value = object.get(getPath());
      if (value == null) {
        return null;
      }
      JSONString strValue = value.isString();
      return strValue == null || strValue.isNull() != null ? null : strValue.stringValue();
    };

    @Override
    public void setValue(final User user, final String value) {
      JSONObject object = user.getNativeData();
      object.put(getPath(), new JSONString(value));
    };
  }

  @FormatterFactories(@FormatterFactory(factory = ShortenFactory.class, name = "shorten"))
  interface Renderer extends XTemplates {
    @XTemplate(source = "UserGrid.html")
    public SafeHtml renderItem(String name, Style style);
  }

  interface Resources extends ClientBundle {
    @Source("UserGrid.css")
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

  ModelKeyProvider<User> key = new ModelKeyProvider<User>() {
    @Override
    public String getKey(final User item) {
      return item.getId();
    }
  };

  @Inject
  ResourceManager placeController;

  private ContentPanel cp;

  private static Renderer r;
  private static Resources resources;

  private User selectedItem;
  private Grid<User> grid;
  private ListView<User, User> listView;
  private VerticalLayoutContainer con;

  static {
    r = GWT.create(Renderer.class);
    resources = GWT.create(Resources.class);
    resources.css().ensureInjected();
  }

  private ListLoader<UserGrid, ListLoadResult<User>> loader;

  public UserGrid() {
    final Style style = resources.css();

    JSONFeedReader reader = new JSONFeedReader();

    PlaceProxy<UserGrid> proxy = new PlaceProxy<UserGrid>();

    loader = new ListLoader<UserGrid, ListLoadResult<User>>(proxy, reader);

    loader.addLoadExceptionHandler(new LoadExceptionHandler<UserGrid>() {
      @Override
      public void onLoadException(final LoadExceptionEvent<UserGrid> event) {
        System.out.println(event.getException());
      }
    });

    ListStore<User> store = new ListStore<User>(key);
    loader.addLoadHandler(new LoadResultListStoreBinding<UserGrid, User, ListLoadResult<User>>(
        store));

    ColumnConfig<User, String> cc1 =
        new ColumnConfig<User, String>(new JSONStringValueProvider("name"), 100, "Sender");
    ColumnConfig<User, String> cc2 =
        new ColumnConfig<User, String>(new JSONStringValueProvider("email"), 165, "Email");
    ColumnConfig<User, String> cc3 =
        new ColumnConfig<User, String>(new JSONStringValueProvider("phone"), 100, "Phone");
    ColumnConfig<User, String> cc4 =
        new ColumnConfig<User, String>(new JSONStringValueProvider("state"), 50, "State");
    ColumnConfig<User, String> cc5 =
        new ColumnConfig<User, String>(new JSONStringValueProvider("zip"), 65, "Zip Code");

    List<ColumnConfig<User, ?>> l = new ArrayList<ColumnConfig<User, ?>>();
    l.add(cc1);
    l.add(cc2);
    l.add(cc3);
    l.add(cc4);
    l.add(cc5);
    ColumnModel<User> cm = new ColumnModel<User>(l);

    grid = new Grid<User>(store, cm);
    grid.getView().setForceFit(true);
    grid.setLoader(loader);
    grid.setLoadMask(true);
    grid.setBorders(true);
    grid.getView().setEmptyText("Please hit the load button.");
    // grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    grid.getSelectionModel().addSelectionHandler(new SelectionHandler<User>() {

      @Override
      public void onSelection(final SelectionEvent<User> event) {
        selectedItem = event.getSelectedItem();
      }
    });

    ListViewCustomAppearance<User> appearance =
        new ListViewCustomAppearance<User>("." + style.thumbWrap(), style.over(), style.select()) {

          @Override
          public void renderEnd(final SafeHtmlBuilder builder) {
            String markup =
                new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append(
                    "\"></div>").toString();
            builder.appendHtmlConstant(markup);
          }

          @Override
          public void renderItem(final SafeHtmlBuilder builder, final SafeHtml content) {
            builder.appendHtmlConstant("<div class='" + style.thumbWrap()
                + "' style='border: 1px solid white'>");
            builder.append(content);
            builder.appendHtmlConstant("</div>");
          }
        };

    listView = new ListView<User, User>(store, new IdentityValueProvider<User>() {
      @Override
      public void setValue(final User object, final User value) {
      }
    }, appearance);
    listView.setCell(new SimpleSafeHtmlCell<User>(new AbstractSafeHtmlRenderer<User>() {

      @Override
      public SafeHtml render(final User user) {
        JSONObject object = user.getNativeData();
        return r.renderItem(object.containsKey("name") ? object.get("name").isString()
            .stringValue() : "", style);
      }
    }));
    // listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    listView.getSelectionModel().addSelectionHandler(new SelectionHandler<User>() {

      @Override
      public void onSelection(final SelectionEvent<User> event) {
        selectedItem = event.getSelectedItem();
      }
    });

    SimpleComboBox<String> type = new SimpleComboBox<String>(new StringLabelProvider<String>());
    type.setTriggerAction(TriggerAction.ALL);
    type.setEditable(false);
    type.setWidth(100);
    type.add("Table");
    type.add("List");
    type.setValue("Table");
    // selectView(type.getValue());
    type.addSelectionHandler(new SelectionHandler<String>() {
      @Override
      public void onSelection(final SelectionEvent<String> event) {
        selectView(event.getSelectedItem());
      }
    });

    ToolBar toolBar = new ToolBar();
    toolBar.add(new LabelToolItem("View:"));
    toolBar.add(type);

    con = new VerticalLayoutContainer();
    con.add(toolBar, new VerticalLayoutData(1, -1));
    con.add(grid, new VerticalLayoutData(1, 1));

    cp = new ContentPanel();
    cp.setHeadingText("Json Grid Example");
    cp.setWidget(con);
    cp.setButtonAlign(BoxLayoutPack.CENTER);
    cp.addButton(new TextButton("Add User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        placeController.goTo(resource.getParent().getRendition(Feed.NEW));
      }
    }));
    cp.addButton(new TextButton("Refresh", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        loader.load(UserGrid.this);
      }
    }));
    cp.addButton(new TextButton("Modify User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (selectedItem == null || selectedItem.equals("")) {
          return;
        }
        placeController.goTo(selectedItem);
      }
    }));
    cp.addButton(new TextButton("Delete User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (selectedItem == null || selectedItem.equals("")) {
          return;
        }
        try {
          RequestProvider.DELETE("api/users/" + selectedItem).contentType(
              RequestFactory.JSON_CONTENT_TYPE_UTF8).send();
          loader.load(UserGrid.this);
        } catch (RequestException e) {
          e.printStackTrace();
        }
      }
    }));
  }

  @Override
  public Resource asResource() {
    return getValue();
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  public List<? extends SortInfo> getSortInfo() {
    List<String> sorts = resource.getQueryParameters().get(Feed.SORT);
    if (sorts != null) {
      List<SortInfo> sortInfo = new ArrayList<SortInfo>();
      for (String sort : sorts) {
        String[] pair = sort.split("|");
        SortInfoBean s = new SortInfoBean();
        s.setSortField(pair[0]);
        s.setSortDir(pair.length > 1 ? SortDir.valueOf(pair[1]) : SortDir.ASC);
        sortInfo.add(s);
      }
      return sortInfo;
    }
    return Collections.EMPTY_LIST;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  @Override
  public void setSortInfo(List<? extends SortInfo> info) {
    for (SortInfo sort : info) {
      String value = sort.getSortField() + "|" + sort.getSortDir();
      resource.getQueryParameters().add(Feed.SORT, value);
    }
  }

  @Override
  public void setValue(Resource resource) {
    super.setValue(resource);
    loader.load(this);
  }

  private void selectView(final String viewName) {
    boolean cell = viewName.equals("Table");
    if (cell) {
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
