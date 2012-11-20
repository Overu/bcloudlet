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

import org.cloudlet.web.core.shared.Property;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.ResourceProxy;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.Root;

import java.util.ArrayList;
import java.util.List;

public class PlaceTree extends BorderLayoutContainer {

  class JSONFeedReader implements DataReader<List<Resource>, String> {

    @Override
    public List<Resource> read(final Object loadConfig, final String data) {
      Resource parent = loadConfig == null ? root : (Resource) loadConfig;
      JSONObject dg = JSONParser.parseLenient(data).isObject();
      JSONObject root = dg.get("dataGraph").isObject().get("root").isObject();
      List<Resource> result = new ArrayList<Resource>();
      result.addAll(parent.getRenditions().values());
      for (Property prop : parent.getResourceType().getAllProperties().values()) {
        if (prop.getType() instanceof ResourceType) {
          Resource res = parent.getRelationship(prop);
          if (res != null) {
            result.add(res);
          } else {
            ResourceProxy proxy = new ResourceProxy((ResourceType) prop.getType());
            proxy.setPath(prop.getPath());
            proxy.setParent(parent);
            proxy.setTitle(prop.getTitle());
            result.add(proxy);
          }
        }
      }
      JSONValue c = root.get(Resource.CHILDREN);
      if (c != null) {
        JSONArray children = c.isArray();
        if (children != null) {
          for (int i = 0; i < children.size(); i++) {
            JSONObject object = children.get(i).isObject();
            Resource child = CoreClientModule.readResource(object);
            parent.addChild(child);
            result.add(child);
          }
        } else {
          JSONObject object = c.isObject();
          Resource child = CoreClientModule.readResource(object);
          parent.addChild(child);
          result.add(child);
        }
      }
      return result;
    }

  }

  private final Resource root;

  @Inject
  ResourceManager resourceManager;

  @Inject
  public PlaceTree(@Root final Resource root) {
    this.root = root;
    ModelKeyProvider<Resource> keyProvider = new ModelKeyProvider<Resource>() {
      @Override
      public String getKey(final Resource item) {
        return item.getUri();
      }
    };

    TreeStore<Resource> store = new TreeStore<Resource>(keyProvider);

    JSONFeedReader reader = new JSONFeedReader();

    PlaceProxy jsonProxy = new PlaceProxy(root);
    final TreeLoader<Resource> loader = new TreeLoader<Resource>(jsonProxy, reader) {
      @Override
      public boolean hasChildren(final Resource parent) {
        return parent.hasChildren();// show relationship on navigation menu
      }
    };
    loader.addLoadHandler(new ChildTreeStoreBinding<Resource>(store));

    final Tree<Resource, String> tree =
        new Tree<Resource, String>(store, new ValueProvider<Resource, String>() {

          @Override
          public String getPath() {
            return "title";
          }

          @Override
          public String getValue(final Resource object) {
            return object.getTitle();
          }

          @Override
          public void setValue(final Resource object, final String value) {
            object.setTitle(value);
          }
        });
    tree.setLoader(loader);
    // tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
    tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Resource>() {
      @Override
      public void onSelection(final SelectionEvent<Resource> event) {
        resourceManager.goTo(event.getSelectedItem());
        tree.getSelectionModel().deselectAll();
      }
    });

    add(tree, new VerticalLayoutData(1, 1));
  }
}
