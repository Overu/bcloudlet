package org.cloudlet.web.core.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.tree.Tree;

import org.cloudlet.web.core.shared.Content;
import org.cloudlet.web.core.shared.Entry;
import org.cloudlet.web.core.shared.HomePlace;
import org.cloudlet.web.core.shared.View;
import org.cloudlet.web.core.shared.WebPlaceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceTree extends BorderLayoutContainer {

  class JSONFeedReader implements DataReader<List<Content>, String> {

    @Override
    public List<Content> read(final Object loadConfig, final String data) {
      Content parent = loadConfig == null ? root : (Content) loadConfig;
      JSONObject dg = JSONParser.parseLenient(data).isObject();
      JSONObject root = dg.get("dataGraph").isObject().get("root").isObject();
      JSONValue c = root.get(Entry.RELATIONSHIPS);
      if (c != null) {
        JSONArray children = c.isArray();
        List<Content> result = new ArrayList<Content>();
        for (int i = 0; i < children.size(); i++) {
          JSONObject object = children.get(i).isObject();
          Content child = CoreClientModule.readContent(object);
          child.setParent(parent);
          parent.getCache().put(child.getPath(), child);
          result.add(child);
        }
        return result;
      } else {
        return Collections.EMPTY_LIST;
      }
    }
  }

  private Content place;

  private final Content root;

  @Inject
  WebPlaceManager placeManager;

  @Inject
  public PlaceTree(@HomePlace final Content root) {
    this.root = root;
    ModelKeyProvider<Content> keyProvider = new ModelKeyProvider<Content>() {
      @Override
      public String getKey(final Content item) {
        return item.getUri();
      }
    };

    TreeStore<Content> store = new TreeStore<Content>(keyProvider);

    JSONFeedReader reader = new JSONFeedReader();

    PlaceProxy jsonProxy = new PlaceProxy(root);
    final TreeLoader<Content> loader = new TreeLoader<Content>(jsonProxy, reader) {
      @Override
      public boolean hasChildren(final Content parent) {
        return parent.getTotalCount() > 0;
      }
    };
    loader.addLoadHandler(new ChildTreeStoreBinding<Content>(store));

    final Tree<Content, String> tree =
        new Tree<Content, String>(store, new ValueProvider<Content, String>() {

          @Override
          public String getPath() {
            return "title";
          }

          @Override
          public String getValue(final Content object) {
            return object.getTitle();
          }

          @Override
          public void setValue(final Content object, final String value) {
            object.setTitle(value);
          }
        });
    tree.setLoader(loader);
    // tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
    tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Content>() {
      @Override
      public void onSelection(final SelectionEvent<Content> event) {
        placeManager.goTo(event.getSelectedItem(), View.HOME);
        tree.getSelectionModel().deselectAll();
      }
    });

    add(tree, new VerticalLayoutData(1, 1));
  }
}
