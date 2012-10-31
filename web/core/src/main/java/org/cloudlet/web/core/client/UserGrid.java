package org.cloudlet.web.core.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
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
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class UserGrid implements IsWidget, EntryPoint {

	class JSONFeedReader implements
			DataReader<ListLoadResult<JSONObject>, String> {

		@Override
		public ListLoadResult<JSONObject> read(Object loadConfig, String data) {
			JSONObject root = JSONParser.parseLenient(data).isObject();
			JSONObject feed = root.get("feed").isObject();
			JSONArray records = feed.get("entries").isArray();
			List<JSONObject> users = new ArrayList<JSONObject>(records.size());
			for (int i = 0; i < records.size(); i++) {
				users.add(records.get(i).isObject());
			}
			ListLoadResultBean<JSONObject> result = new ListLoadResultBean<JSONObject>(
					users);
			return result;
		}
	}

	ModelKeyProvider<JSONObject> key = new ModelKeyProvider<JSONObject>() {
		@Override
		public String getKey(JSONObject item) {
			return "name";
		}
	};

	class JSONStringValueProvider implements ValueProvider<JSONObject, String> {

		public String path;

		JSONStringValueProvider(String path) {
			this.path = path;
		}

		public String getValue(JSONObject object) {
			JSONValue value = object.get(getPath());
			if (value == null)
				return null;
			JSONString strValue = value.isString();
			return strValue == null || strValue.isNull() != null ? null
					: strValue.stringValue();
		};

		public void setValue(JSONObject object, String value) {
			object.put(getPath(), new JSONString(value));
		};

		public String getPath() {
			return path;
		};
	};

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(this);
	}

	@Override
	public Widget asWidget() {

		JSONFeedReader reader = new JSONFeedReader();

		String path = "api/groups/g1/users";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, path);
		builder.setHeader("Accept", "application/json");
		HttpProxy<ListLoadConfig> proxy = new HttpProxy<ListLoadConfig>(builder);

		final ListLoader<ListLoadConfig, ListLoadResult<JSONObject>> loader = new ListLoader<ListLoadConfig, ListLoadResult<JSONObject>>(
				proxy, reader);
		ListLoadConfig loadConfig = new ListLoadConfigBean();
		loader.useLoadConfig(loadConfig);
		loader.addLoadExceptionHandler(new LoadExceptionHandler<ListLoadConfig>() {
			@Override
			public void onLoadException(LoadExceptionEvent<ListLoadConfig> event) {
				System.out.println(event.getException());
			}
		});

		ListStore<JSONObject> store = new ListStore<JSONObject>(key);
		loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, JSONObject, ListLoadResult<JSONObject>>(
				store));

		ColumnConfig<JSONObject, String> cc1 = new ColumnConfig<JSONObject, String>(
				new JSONStringValueProvider("name"), 100, "Sender");
		ColumnConfig<JSONObject, String> cc2 = new ColumnConfig<JSONObject, String>(
				new JSONStringValueProvider("email"), 165, "Email");
		ColumnConfig<JSONObject, String> cc3 = new ColumnConfig<JSONObject, String>(
				new JSONStringValueProvider("phone"), 100, "Phone");
		ColumnConfig<JSONObject, String> cc4 = new ColumnConfig<JSONObject, String>(
				new JSONStringValueProvider("state"), 50, "State");
		ColumnConfig<JSONObject, String> cc5 = new ColumnConfig<JSONObject, String>(
				new JSONStringValueProvider("zip"), 65, "Zip Code");

		List<ColumnConfig<JSONObject, ?>> l = new ArrayList<ColumnConfig<JSONObject, ?>>();
		l.add(cc1);
		l.add(cc2);
		l.add(cc3);
		l.add(cc4);
		l.add(cc5);
		ColumnModel<JSONObject> cm = new ColumnModel<JSONObject>(l);

		Grid<JSONObject> grid = new Grid<JSONObject>(store, cm);
		grid.getView().setForceFit(true);
		grid.setLoader(loader);
		grid.setLoadMask(true);
		grid.setBorders(true);
		grid.getView().setEmptyText("Please hit the load button.");

		FramedPanel cp = new FramedPanel();
		cp.setHeadingText("Json Grid Example");
		cp.setCollapsible(true);
		cp.setAnimCollapse(true);
		cp.setWidget(grid);
		cp.setPixelSize(575, 350);
		cp.addStyleName("margin-10");
		cp.setButtonAlign(BoxLayoutPack.CENTER);
		cp.addButton(new TextButton("Load Json", new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				loader.load();
			}
		}));

		return cp;
	}

}
