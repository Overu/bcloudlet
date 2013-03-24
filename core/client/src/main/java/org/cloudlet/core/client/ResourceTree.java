package org.cloudlet.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.ChildTreeStoreBinding;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.DataReader;
import com.sencha.gxt.data.shared.loader.TreeLoader;
import com.sencha.gxt.theme.gray.client.tree.GrayTreeAppearance;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.tree.Tree;

import org.cloudlet.core.server.Content;
import org.cloudlet.core.server.Item;

import java.util.ArrayList;
import java.util.List;

public class ResourceTree extends BorderLayoutContainer implements TakesResource {
  class JSONFeedReader implements DataReader<List<Resource>, Resource> {
    @Override
    public List<Resource> read(final Object loadConfig, final Resource resource) {
      List<Resource> result = new ArrayList<Resource>();
      List<Resource> children = resource.getList(Item.REFERENCES);
      for (Resource child : children) {
        result.add(child);
      }
      return result;
    }
  }

  @Inject
  ResourceManager resourceManager;

  TreeLoader<Resource> loader;

  Tree<Resource, String> tree;

  TreeStore<Resource> store;

  private boolean initialized = false;

  private Resource root;

  @Override
  public Resource getValue() {
    return root;
  }

  @Override
  public void setValue(Resource place) {
    this.root = place;
  }

  protected void initView() {
    ModelKeyProvider<Resource> keyProvider = new ModelKeyProvider<Resource>() {
      @Override
      public String getKey(final Resource item) {
        return Integer.toString(item.hashCode());
        // StringBuilder b = item.getUriBuilder(false);
        // return b.toString();
      }
    };
    store = new TreeStore<Resource>(keyProvider);

    JSONFeedReader reader = new JSONFeedReader();

    loader = new TreeLoader<Resource>(new DataProxy<Resource, Resource>() {
      @Override
      public void load(Resource resource, final Callback<Resource, Throwable> callback) {
        resource.getQueryParameters().addFirst(Item.REFERENCES, "true");
        resource.load(new AsyncCallback<Resource>() {
          @Override
          public void onFailure(Throwable caught) {
            callback.onFailure(caught);
          };

          @Override
          public void onSuccess(Resource result) {
            callback.onSuccess(result);
          }
        });
      }

    }, reader) {
      @Override
      public boolean hasChildren(final Resource resource) {
        return resource.hasChildren();
      }
    };
    loader.addLoadHandler(new ChildTreeStoreBinding<Resource>(store));
    tree = new Tree<Resource, String>(store, new ValueProvider<Resource, String>() {

      @Override
      public String getPath() {
        return Content.TITLE;
      }

      @Override
      public String getValue(final Resource res) {
        return res.getTitle();
      }

      @Override
      public void setValue(final Resource res, final String value) {
        res.setTitle(value);
      }
    }, new GrayTreeAppearance());

    tree.setLoader(loader);
    // tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
    tree.getSelectionModel().addSelectionHandler(new SelectionHandler<Resource>() {
      @Override
      public void onSelection(final SelectionEvent<Resource> event) {
        resourceManager.goTo(event.getSelectedItem());
        // tree.getSelectionModel().deselectAll();
      }
    });

    add(tree, new VerticalLayoutData(1, 1));
    store.add(root);
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }
}
