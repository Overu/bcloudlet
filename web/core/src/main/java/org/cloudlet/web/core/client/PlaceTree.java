package org.cloudlet.web.core.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.inject.Inject;
import com.google.inject.Provider;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.HttpProxy;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.tree.Tree;

import org.cloudlet.web.core.shared.CorePackage;
import org.cloudlet.web.core.shared.HomePlace;
import org.cloudlet.web.core.shared.PlaceType;
import org.cloudlet.web.core.shared.WebPlace;
import org.cloudlet.web.core.shared.WebPlaceManager;

import java.util.ArrayList;
import java.util.List;

public class PlaceTree extends BorderLayoutContainer {

  class JSONFeedReader implements DataReader<List<WebPlace>, String> {

    @Override
    public List<WebPlace> read(final Object loadConfig, final String data) {
      JSONObject root = JSONParser.parseLenient(data).isObject();
      JSONObject feed = root.get("repository").isObject();
      JSONArray children = feed.get(CorePackage.Content.CHILDREN).isArray();

      List<WebPlace> result = new ArrayList<WebPlace>();
      for (int i = 0; i < children.size(); i++) {
        WebPlace place = placeProvider.get();
        JSONObject object = children.get(i).isObject();
        place.setPath(object.get("path").isString().stringValue());
        place.setTitle(object.get("title").isString().stringValue());
        String targetTypeName = object.get("@xsi.type").isString().stringValue();
        PlaceType targetType = PlaceType.getType(targetTypeName);
        place.setPlaceType(targetType);
        rootPlace.addChild(place);
        result.add(place);
      }
      return result;
    }
  }

  private WebPlace place;
  private final WebPlace rootPlace;
  private final Provider<WebPlace> placeProvider;

  @Inject
  WebPlaceManager placeManager;

  @Inject
  public PlaceTree(@HomePlace final WebPlace rootPlace, final Provider<WebPlace> placeProvider) {
    this.rootPlace = rootPlace;
    this.placeProvider = placeProvider;
    ModelKeyProvider<WebPlace> keyProvider = new ModelKeyProvider<WebPlace>() {
      @Override
      public String getKey(final WebPlace item) {
        return item.getUri();
      }
    };

    TreeStore<WebPlace> store = new TreeStore<WebPlace>(keyProvider);

    JSONFeedReader reader = new JSONFeedReader();

    RequestBuilder rb =
        new RequestBuilder(RequestBuilder.GET, "api/?" + CorePackage.Content.CHILDREN + "=true");
    rb.setHeader("Accept", "application/json");
    HttpProxy<WebPlace> jsonProxy = new HttpProxy<WebPlace>(rb);
    final TreeLoader<WebPlace> loader = new TreeLoader<WebPlace>(jsonProxy, reader) {
      @Override
      public boolean hasChildren(final WebPlace parent) {
        return parent.isFolder();
      }
    };
    loader.addLoadHandler(new ChildTreeStoreBinding<WebPlace>(store));

    final Tree<WebPlace, String> tree =
        new Tree<WebPlace, String>(store, new ValueProvider<WebPlace, String>() {

          @Override
          public String getPath() {
            return "title";
          }

          @Override
          public String getValue(final WebPlace object) {
            return object.getTitle();
          }

          @Override
          public void setValue(final WebPlace object, final String value) {
            object.setTitle(value);
          }
        });
    tree.setLoader(loader);
    // tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
    tree.getSelectionModel().addSelectionHandler(new SelectionHandler<WebPlace>() {
      @Override
      public void onSelection(final SelectionEvent<WebPlace> event) {
        placeManager.goTo(event.getSelectedItem(), WebPlace.HOME);
        tree.getSelectionModel().deselectAll();
      }
    });

    ToolBar buttonBar = new ToolBar();

    buttonBar.add(new TextButton("Expand All", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        loader.load();
        // tree.expandAll();
      }
    }));

    buttonBar.add(new TextButton("Collapse All", new SelectHandler() {
      @Override
      public void onSelect(final SelectEvent event) {
        tree.collapseAll();
      }

    }));

    add(buttonBar, new VerticalLayoutData(1, -1));
    add(tree, new VerticalLayoutData(1, 1));
  }
}
