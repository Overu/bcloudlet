package org.cloudlet.web.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadConfigBean;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadExceptionEvent;
import com.sencha.gxt.data.shared.loader.LoadExceptionEvent.LoadExceptionHandler;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.List;

public class UserGrid implements IsWidget, EntryPoint {

  class JSONFeedReader implements DataReader<ListLoadResult<JSONObject>, String> {

    @Override
    public ListLoadResult<JSONObject> read(final Object loadConfig, final String data) {
      JSONObject root = JSONParser.parseLenient(data).isObject();
      JSONObject feed = root.get("userFeed").isObject();
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

  ModelKeyProvider<JSONObject> key = new ModelKeyProvider<JSONObject>() {
    @Override
    public String getKey(final JSONObject item) {
      return "name";
    }
  };;

  @Override
  public Widget asWidget() {

    JSONFeedReader reader = new JSONFeedReader();

    String path = "api/groups/mygroup/users";
    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
    builder.setHeader("Accept", "application/json");
    HttpProxy<ListLoadConfig> proxy = new HttpProxy<ListLoadConfig>(builder);

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

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    final TextField name = new TextField();
    name.setAllowBlank(false);
    name.setName("name");
    p.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));

    final TextField email = new TextField();
    email.setAllowBlank(false);
    email.setName("email");
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));

    final TextField phone = new TextField();
    phone.setName("phone");
    p.add(new FieldLabel(phone, "phone"), new VerticalLayoutData(1, -1));

    final TextField state = new TextField();
    state.setName("state");
    p.add(new FieldLabel(state, "state"), new VerticalLayoutData(1, -1));

    final TextField zip = new TextField();
    zip.setName("zip");
    p.add(new FieldLabel(zip, "zip"), new VerticalLayoutData(1, -1));

    final FormPanel formPanel = new FormPanel();
    // formPanel.setAction("api/groups/mygroup/users");
    // formPanel.setMethod(Method.POST);
    // formPanel.setEncoding(Encoding.URLENCODED);
    formPanel.add(p);

    FramedPanel cp1 = new FramedPanel();
    cp1.setHeadingText("Form Panel");
    cp1.setCollapsible(true);
    cp1.setWidget(formPanel);
    cp1.setWidth(300);

    Grid<JSONObject> grid = new Grid<JSONObject>(store, cm);
    grid.getView().setForceFit(true);
    grid.setLoader(loader);
    grid.setLoadMask(true);
    grid.setBorders(true);
    grid.getView().setEmptyText("Please hit the load button.");

    FramedPanel cp2 = new FramedPanel();
    cp2.setHeadingText("Json Grid Example");
    cp2.setCollapsible(true);
    cp2.setAnimCollapse(true);
    cp2.setWidget(grid);
    cp2.setPixelSize(575, 350);
    cp2.addStyleName("margin-10");
    cp2.setButtonAlign(BoxLayoutPack.CENTER);

    final RequestBuilder builder1 =
        new RequestBuilder(RequestBuilder.POST, "api/groups/mygroup/users");
    builder1.setHeader("Content-Type", RequestFactory.JSON_CONTENT_TYPE_UTF8);
    builder1.setCallback(new RequestCallback() {
      @Override
      public void onError(final Request request, final Throwable exception) {
      }

      @Override
      public void onResponseReceived(final Request request, final Response response) {
      }

    });
    cp2.addButton(new TextButton("Add User", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (name.getValue() == null || email.getValue() == null) {
          Info.display("name or email is Null", "");
          return;
        }
        JSONObject json = new JSONObject();
        json.put("name", new JSONString(getParameter(name.getValue())));
        json.put("email", new JSONString(getParameter(email.getValue())));
        json.put("phone", new JSONString(getParameter(phone.getValue())));
        json.put("state", new JSONString(getParameter(state.getValue())));
        json.put("zip", new JSONString(getParameter(zip.getValue())));
        try {
          builder1.setRequestData("{" + "\"user\":" + json.toString() + "}");
          builder1.send();
        } catch (RequestException e) {
          e.printStackTrace();
        }
        loader.load();
      }
    }));
    cp2.addButton(new TextButton("Load Json", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        loader.load();
      }
    }));

    HTMLPanel root = new HTMLPanel("");
    root.add(cp1);
    root.add(cp2);

    return root;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  private String getParameter(final String value) {
    return value == null ? "" : value;
  }

}
