package org.cloudlet.web.core.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
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
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.StringLabelProvider;
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
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.ResourceWidget;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;

import java.util.ArrayList;
import java.util.List;

public class UserGrid extends ContentPanel implements ResourceWidget<UserFeed> {

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

  interface UserPorperties extends PropertyAccess<User> {
    ValueProvider<User, String> email();

    ModelKeyProvider<User> id();

    ValueProvider<User, String> name();

    ValueProvider<User, String> phone();

    ValueProvider<User, String> state();

    ValueProvider<User, String> zip();
  }

  @Inject
  ResourceManager placeController;

  private static Renderer r;
  private static Resources resources;

  private User selectedItem;
  private Grid<User> grid;
  private ListView<User, User> listView;
  private VerticalLayoutContainer con;
  private ListStore<User> store;
  private ResourceProxy<UserFeed> proxy;

  private static UserPorperties properties = GWT.create(UserPorperties.class);

  static {
    r = GWT.create(Renderer.class);
    resources = GWT.create(Resources.class);
    resources.css().ensureInjected();
  }

  public UserGrid() {
    final Style style = resources.css();

    proxy = new ResourceProxy<UserFeed>();
    store = new ListStore<User>(properties.id());

    ColumnConfig<User, String> cc1 =
        new ColumnConfig<User, String>(properties.name(), 100, "Sender");
    ColumnConfig<User, String> cc2 =
        new ColumnConfig<User, String>(properties.email(), 165, "Email");
    ColumnConfig<User, String> cc3 =
        new ColumnConfig<User, String>(properties.phone(), 100, "Phone");
    ColumnConfig<User, String> cc4 =
        new ColumnConfig<User, String>(properties.state(), 50, "State");
    ColumnConfig<User, String> cc5 =
        new ColumnConfig<User, String>(properties.zip(), 65, "Zip Code");

    List<ColumnConfig<User, ?>> l = new ArrayList<ColumnConfig<User, ?>>();
    l.add(cc1);
    l.add(cc2);
    l.add(cc3);
    l.add(cc4);
    l.add(cc5);
    ColumnModel<User> cm = new ColumnModel<User>(l);

    grid = new Grid<User>(store, cm);
    grid.getView().setForceFit(true);
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

    setHeadingText("Json Grid Example");
    setWidget(con);
    setButtonAlign(BoxLayoutPack.CENTER);
    addButton(new TextButton("Add User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        placeController.goTo(getResource().getRendition(Feed.NEW));
      }
    }));
    addButton(new TextButton("Refresh", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        load();
        // loader.load(getValue());
      }
    }));
    addButton(new TextButton("Edit", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (selectedItem == null || selectedItem.equals("")) {
          return;
        }
        placeController.goTo(selectedItem);
      }
    }));
    addButton(new TextButton("Delete User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (selectedItem == null || selectedItem.equals("")) {
          return;
        }
        try {
          RequestProvider.DELETE("api/users/" + selectedItem).contentType(
              RequestFactory.JSON_CONTENT_TYPE_UTF8).send();
          // loader.load(getValue());
        } catch (RequestException e) {
          e.printStackTrace();
        }
      }
    }));
  }

  @Override
  public UserFeed getResource() {
    return (UserFeed) getData(Resource.class.getName());
  }

  @Override
  public void setResource(final UserFeed resource) {
    setData(Resource.class.getName(), resource);
    load();
  }

  private void load() {
    proxy.load(getResource(), new com.google.gwt.core.client.Callback<UserFeed, Throwable>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final UserFeed result) {
        List<User> users = result.getEntries();
        store.replaceAll(users);
      }
    });
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
