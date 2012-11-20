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
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadConfigBean;
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
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebView;

import java.util.ArrayList;
import java.util.List;

public class UserGrid extends WebView implements IsWidget, EntryPoint {

  class JSONFeedReader implements DataReader<ListLoadResult<JSONObject>, String> {

    @Override
    public ListLoadResult<JSONObject> read(final Object loadConfig, final String data) {
      JSONObject root = JSONParser.parseLenient(data).isObject();
      JSONObject feed = root.get("dataGraph").isObject().get("root").isObject();
      JSONValue entries = feed.get("entries");
      JSONArray records;
      List<JSONObject> users = new ArrayList<JSONObject>();
      if (entries != null && (records = entries.isArray()) != null) {
        for (int i = 0; i < records.size(); i++) {
          users.add(records.get(i).isObject());
        }
      }
      ListLoadResultBean<JSONObject> result = new ListLoadResultBean<JSONObject>(users);
      return result;
    }
  }

  class JSONStringValueProvider implements ValueProvider<JSONObject, String> {

    public String path;

    JSONStringValueProvider(final String path) {
      this.path = path;
    }

    @Override
    public String getPath() {
      return path;
    };

    @Override
    public String getValue(final JSONObject object) {
      JSONValue value = object.get(getPath());
      if (value == null) {
        return null;
      }
      JSONString strValue = value.isString();
      return strValue == null || strValue.isNull() != null ? null : strValue.stringValue();
    };

    @Override
    public void setValue(final JSONObject object, final String value) {
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

  ModelKeyProvider<JSONObject> key = new ModelKeyProvider<JSONObject>() {
    @Override
    public String getKey(final JSONObject item) {
      return "name";
    }
  };

  @Inject
  ResourceManager placeController;

  private ContentPanel cp;

  private static Renderer r;
  private static Resources resources;

  private User userPath;
  private Grid<JSONObject> grid;
  private ListView<JSONObject, JSONObject> listView;
  private VerticalLayoutContainer con;

  static {
    r = GWT.create(Renderer.class);
    resources = GWT.create(Resources.class);
    resources.css().ensureInjected();
  }

  public UserGrid() {
    final Style style = resources.css();

    JSONFeedReader reader = new JSONFeedReader();

    String path = "api/users";
    // RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
    // builder.setHeader("Accept", "application/json");
    HttpProxy<ListLoadConfig> proxy =
        new HttpProxy<ListLoadConfig>(RequestProvider.GET(path).accept("application/json"));

    final ListLoader<ListLoadConfig, ListLoadResult<JSONObject>> loader =
        new ListLoader<ListLoadConfig, ListLoadResult<JSONObject>>(proxy, reader);
    ListLoadConfig loadConfig = new ListLoadConfigBean();
    loader.useLoadConfig(loadConfig);
    loader.addLoadExceptionHandler(new LoadExceptionHandler<ListLoadConfig>() {
      @Override
      public void onLoadException(final LoadExceptionEvent<ListLoadConfig> event) {
        System.out.println(event.getException());
      }
    });

    ListStore<JSONObject> store = new ListStore<JSONObject>(key);
    loader
        .addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, JSONObject, ListLoadResult<JSONObject>>(
            store));

    ColumnConfig<JSONObject, String> cc1 =
        new ColumnConfig<JSONObject, String>(new JSONStringValueProvider("name"), 100, "Sender");
    ColumnConfig<JSONObject, String> cc2 =
        new ColumnConfig<JSONObject, String>(new JSONStringValueProvider("email"), 165, "Email");
    ColumnConfig<JSONObject, String> cc3 =
        new ColumnConfig<JSONObject, String>(new JSONStringValueProvider("phone"), 100, "Phone");
    ColumnConfig<JSONObject, String> cc4 =
        new ColumnConfig<JSONObject, String>(new JSONStringValueProvider("state"), 50, "State");
    ColumnConfig<JSONObject, String> cc5 =
        new ColumnConfig<JSONObject, String>(new JSONStringValueProvider("zip"), 65, "Zip Code");

    List<ColumnConfig<JSONObject, ?>> l = new ArrayList<ColumnConfig<JSONObject, ?>>();
    l.add(cc1);
    l.add(cc2);
    l.add(cc3);
    l.add(cc4);
    l.add(cc5);
    ColumnModel<JSONObject> cm = new ColumnModel<JSONObject>(l);

    grid = new Grid<JSONObject>(store, cm);
    grid.getView().setForceFit(true);
    grid.setLoader(loader);
    grid.setLoadMask(true);
    grid.setBorders(true);
    grid.getView().setEmptyText("Please hit the load button.");
    // grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    grid.getSelectionModel().addSelectionHandler(new SelectionHandler<JSONObject>() {

      @Override
      public void onSelection(final SelectionEvent<JSONObject> event) {
        setUserPath(event.getSelectedItem());
      }
    });

    ListViewCustomAppearance<JSONObject> appearance =
        new ListViewCustomAppearance<JSONObject>("." + style.thumbWrap(), style.over(), style
            .select()) {

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

    listView = new ListView<JSONObject, JSONObject>(store, new IdentityValueProvider<JSONObject>() {
      @Override
      public void setValue(final JSONObject object, final JSONObject value) {
      }
    }, appearance);
    listView.setCell(new SimpleSafeHtmlCell<JSONObject>(new AbstractSafeHtmlRenderer<JSONObject>() {

      @Override
      public SafeHtml render(final JSONObject object) {
        return r.renderItem(object.containsKey("name") ? object.get("name").isString()
            .stringValue() : "", style);
      }
    }));
    // listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    listView.getSelectionModel().addSelectionHandler(new SelectionHandler<JSONObject>() {

      @Override
      public void onSelection(final SelectionEvent<JSONObject> event) {
        setUserPath(event.getSelectedItem());
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
    cp.addButton(new TextButton("Load Json", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        loader.load();
      }
    }));
    cp.addButton(new TextButton("Modify User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (userPath == null || userPath.equals("")) {
          return;
        }
        placeController.goTo(userPath);
      }
    }));
    cp.addButton(new TextButton("Delete User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (userPath == null || userPath.equals("")) {
          return;
        }
        try {
          RequestProvider.DELETE("api/users/" + userPath).contentType(
              RequestFactory.JSON_CONTENT_TYPE_UTF8).send();
          loader.load();
        } catch (RequestException e) {
          e.printStackTrace();
        }
      }
    }));
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(this);
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

  private void setUserPath(final JSONObject object) {
    if (object == null) {
      userPath = null;
    } else {
      userPath = (User) CoreClientModule.readResource(object);
      UserFeed feed = (UserFeed) getResource().getParent();
      userPath.setParent(feed);
    }
  }
}
