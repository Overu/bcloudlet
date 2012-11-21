package org.cloudlet.web.core.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.theme.gray.client.tree.GrayTreeAppearance;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.tree.Tree;

import org.cloudlet.web.core.shared.DynaResource;
import org.cloudlet.web.core.shared.Property;
import org.cloudlet.web.core.shared.Rendition;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.Root;
import org.cloudlet.web.core.shared.WebView;

import java.util.ArrayList;
import java.util.List;

public class ResourceTree extends WebView implements IsWidget {

  class JSONFeedReader implements DataReader<List<Resource>, String> {

    @Override
    public List<Resource> read(final Object loadConfig, final String data) {
      Resource parent = (Resource) loadConfig;
      List<Resource> result = new ArrayList<Resource>();
      for (Rendition rendition : parent.getRenditions().values()) {
        if (rendition.getPath().equals(Resource.HOME)) {
          continue;
        }
        result.add(rendition);
      }
      for (Property prop : parent.getResourceType().getAllProperties().values()) {
        if (prop.getType() instanceof ResourceType) {
          Resource res = parent.getRelationship(prop);
          if (res != null) {
            result.add(res);
          } else {
            DynaResource proxy = new DynaResource();
            proxy.setParent(parent);
            proxy.setPath(prop.getPath());
            proxy.setTitle(prop.getTitle());
            result.add(proxy);
          }
        }
      }

      JSONObject dg = JSONParser.parseLenient(data).isObject();
      JSONObject root = dg.get("dataGraph").isObject().get("root").isObject();
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

  BorderLayoutContainer con;

  @Inject
  ResourceManager resourceManager;

  TreeLoader<Resource> loader;

  Tree<Resource, String> tree;

  TreeStore<Resource> store;

  @Inject
  public ResourceTree(@Root Resource root) {
    con = new BorderLayoutContainer();
    ModelKeyProvider<Resource> keyProvider = new ModelKeyProvider<Resource>() {
      @Override
      public String getKey(final Resource item) {
        return item.getUri();
      }
    };

    store = new TreeStore<Resource>(keyProvider);
    store.add(root);
    JSONFeedReader reader = new JSONFeedReader();

    ResourceProxy<Resource> jsonProxy = new ResourceProxy<Resource>() {
      @Override
      protected void onRequest(Resource config) {
        config.getRendition().getQueryParameters().addFirst(Resource.CHILDREN, "true");
      };
    };
    loader = new TreeLoader<Resource>(jsonProxy, reader) {
      @Override
      public boolean hasChildren(final Resource parent) {
        return parent.hasChildren();// show relationship on navigation menu
      }
    };
    loader.addLoadHandler(new ChildTreeStoreBinding<Resource>(store));
    tree = new Tree<Resource, String>(store, new ValueProvider<Resource, String>() {

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
    }, new GrayTreeAppearance());

    tree.setLoader(loader);
    // tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
    tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Resource>() {
      @Override
      public void onSelection(final SelectionEvent<Resource> event) {
        resourceManager.goTo(event.getSelectedItem());
        tree.getSelectionModel().deselectAll();
      }
    });

    con.add(tree, new VerticalLayoutData(1, 1));
  }

  @Override
  public Widget asWidget() {
    return con;
  }

  @Override
  public void setValue(Resource resource) {
    // if (resource != this.resource) {
    // store.clear();
    // store.add(resource);
    // loader.load(resource);
    // }
    super.setValue(resource);
  }
}
