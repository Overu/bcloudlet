package org.cloudlet.web.core.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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

import org.cloudlet.web.core.Registry;
import org.cloudlet.web.core.Resource;
import org.cloudlet.web.core.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceTree<T extends Resource> extends BorderLayoutContainer implements ResourceWidget<T> {

  class JSONFeedReader implements DataReader<List<ResourcePlace>, ResourcePlace> {

    @Override
    public List<ResourcePlace> read(final Object loadConfig, final ResourcePlace place) {
      Resource resource = place.getResource();

      List<ResourcePlace> result = new ArrayList<ResourcePlace>();
      String resourceType = resource.getResourceType();
      Map<String, Object> widgets = Registry.getWidgets(resourceType);
      for (String kind : widgets.keySet()) {
        if (Resource.SELF.equals(kind)) {
          continue;
        }
        Object widget = widgets.get(kind);
        ResourcePlace rendition = place.getHost().getRendition(kind);
        rendition.setWidget(widget);
        result.add(rendition);
      }

      List<Resource> children = resource.getChildren();
      if (children != null) {
        for (Resource child : children) {
          ResourcePlace childPlace = place.getHost().getChild(child.getPath());
          childPlace.setResource(child);
          ResourcePlace self = childPlace.getRendition(Resource.SELF);
          self.getQueryParameters().addFirst(Resource.CHILDREN, "true");
          result.add(self);
        }
      }
      return result;
    }

  }

  @Inject
  ResourceManager resourceManager;

  TreeLoader<ResourcePlace> loader;

  Tree<ResourcePlace, String> tree;

  TreeStore<ResourcePlace> store;

  private boolean initialized = false;

  @Root
  @Inject
  ResourcePlace root;

  private ResourcePlace<T> place;

  @Override
  public ResourcePlace<T> getPlace() {
    return place;
  }

  @Override
  public Class<T> getResourceType() {
    return (Class<T>) Resource.class;
  }

  @Override
  public void setPlace(ResourcePlace<T> place) {
    this.place = place;
    // if (resource != this.resource) {
    // store.clear();
    // store.add(resource);
    // loader.load(resource);
    // }
  }

  protected void initView() {
    ModelKeyProvider<ResourcePlace> keyProvider = new ModelKeyProvider<ResourcePlace>() {
      @Override
      public String getKey(final ResourcePlace item) {
        return item.getUri();
      }
    };

    store = new TreeStore<ResourcePlace>(keyProvider);

    JSONFeedReader reader = new JSONFeedReader();

    ResourceProxy jsonProxy = new ResourceProxy();
    loader = new TreeLoader<ResourcePlace>(jsonProxy, reader) {
      @Override
      public boolean hasChildren(final ResourcePlace parent) {
        return parent.hasChildren();
      }
    };
    loader.addLoadHandler(new ChildTreeStoreBinding<ResourcePlace>(store));
    tree = new Tree<ResourcePlace, String>(store, new ValueProvider<ResourcePlace, String>() {

      @Override
      public String getPath() {
        return "title";
      }

      @Override
      public String getValue(final ResourcePlace object) {
        return object.getTitle();
      }

      @Override
      public void setValue(final ResourcePlace object, final String value) {
        object.setTitle(value);
      }
    }, new GrayTreeAppearance());

    tree.setLoader(loader);
    // tree.getStyle().setLeafIcon(ExampleImages.INSTANCE.music());
    tree.getSelectionModel().addSelectionHandler(new SelectionHandler<ResourcePlace>() {
      @Override
      public void onSelection(final SelectionEvent<ResourcePlace> event) {
        resourceManager.goTo(event.getSelectedItem());
        tree.getSelectionModel().deselectAll();
      }
    });

    add(tree, new VerticalLayoutData(1, 1));
    ResourcePlace home = root.getRendition(Resource.SELF);
    home.getQueryParameters().addFirst(Resource.CHILDREN, "true");
    store.add(home);
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
