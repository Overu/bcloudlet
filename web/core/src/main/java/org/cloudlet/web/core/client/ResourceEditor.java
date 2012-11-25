package org.cloudlet.web.core.client;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorContext;
import com.google.gwt.editor.client.EditorVisitor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import org.cloudlet.web.core.Resource;

public abstract class ResourceEditor<T extends Resource> extends ContentPanel implements Editor<T>,
    ResourceWidget<T> {

  class ValueVisitor<V> extends EditorVisitor {

    private V value;

    @Override
    public <M> boolean visit(EditorContext<M> ctx) {
      M value = ctx.getFromModel();
      return super.visit(ctx);
    }
  }

  @Inject
  protected ResourceProxy<ResourcePlace<T>> proxy;

  @Inject
  ResourceManager resourceManager;

  private boolean initialized = false;

  private ValueVisitor<T> visitor = new ValueVisitor<T>();

  private ResourcePlace<T> place;

  private AutoBean<T> bean;

  @Override
  public ResourcePlace<T> getPlace() {
    return place;
  }

  @Override
  public void setPlace(ResourcePlace<T> place) {
    this.place = place;
    ensureInitialized();
    T res = place.getResource();
    getDriver().edit(res);
    bean = AutoBeanUtils.getAutoBean(res);
  }

  protected abstract <D extends SimpleBeanEditorDriver<T, ResourceEditor<T>>> D getDriver();

  protected void initView() {
    addButton(new TextButton("Save", new SelectHandler() {
      @Override
      public void onSelect(final SelectEvent event) {
        T resource = getDriver().flush();
        save(resource);
      }
    }));
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  protected void save(final T resource) {
    RequestBuilder.Method method =
        resource.getId() == null ? RequestBuilder.POST : RequestBuilder.PUT;
    place.execute(method, new AsyncCallback<ResourcePlace<T>>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final ResourcePlace<T> result) {
        ResourcePlace place = result.getParent().getRendition(UserGrid.LIST);
        resourceManager.goTo(place);
      }
    });
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
      getDriver().initialize(this);
    }
  }

}
