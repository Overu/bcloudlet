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

import org.cloudlet.web.core.shared.DynaResource;
import org.cloudlet.web.core.shared.Property;
import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.ResourceType;
import org.cloudlet.web.core.shared.ResourceWidget;
import org.cloudlet.web.core.shared.Root;

import java.util.ArrayList;
import java.util.List;

public class ResourceTree extends BorderLayoutContainer implements ResourceWidget<Resource> {

  class JSONFeedReader implements DataReader<List<Resource>, Resource> {

    @Override
    public List<Resource> read(final Object loadConfig, final Resource data) {
      Resource parent = (Resource) loadConfig;
      parent = parent.getSelf();
      List<Resource> result = new ArrayList<Resource>();
      for (Resource res : parent.getRenditions().values()) {
        if (res.getRenditionKind().equals(Resource.SELF)) {
          continue;
        }
        res.getQueryParameters().addFirst(Resource.CHILDREN, "true");
        result.add(res);
      }
      for (Property prop : parent.getResourceType().getAllProperties().values()) {
        if (prop.getType() instanceof ResourceType) {
          Resource res = parent.getRelationship(prop);
          if (res != null) {
            result.add(res);
          } else {
            res = new DynaResource();
            res.setParent(parent);
            res.setPath(prop.getPath());
            res.setTitle(prop.getTitle());
            result.add(res);
          }
          res.getQueryParameters().addFirst(Resource.CHILDREN, "true");
        }
      }
      if (data.getChildren() != null) {
        for (Resource child : data.getChildren()) {
          child.getQueryParameters().addFirst(Resource.CHILDREN, "true");
          result.add(child);
        }
      }
      return result;
    }

  }

  @Inject
  ResourceManager resourceManager;

  TreeLoader<Resource> loader;

  Tree<Resource, String> tree;

  TreeStore<Resource> store;

  @Inject
  public ResourceTree(@Root final Resource root) {
    ModelKeyProvider<Resource> keyProvider = new ModelKeyProvider<Resource>() {
      @Override
      public String getKey(final Resource item) {
        StringBuilder builder = item.getSelf().getUriBuilder().append("@");
        String kind = item.getRenditionKind();
        if (kind != null) {
          builder.append(kind);
        }
        return builder.toString();
      }
    };

    store = new TreeStore<Resource>(keyProvider);
    Resource home = root.getRendition(Resource.SELF);
    home.getQueryParameters().addFirst(Resource.CHILDREN, "true");
    store.add(home);
    JSONFeedReader reader = new JSONFeedReader();

    ResourceProxy<Resource> jsonProxy = new ResourceProxy<Resource>();
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

    add(tree, new VerticalLayoutData(1, 1));
  }

  @Override
  public Resource getResource() {
    return (Resource) getData(Resource.class.getName());
  }

  @Override
  public void setResource(final Resource resource) {
    setData(Resource.class.getName(), resource);
    // if (resource != this.resource) {
    // store.clear();
    // store.add(resource);
    // loader.load(resource);
    // }
  }
}
