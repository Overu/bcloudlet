package org.cloudlet.web.core.client;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorContext;
import com.google.gwt.editor.client.EditorVisitor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.http.client.RequestBuilder;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.ResourceWidget;
import org.cloudlet.web.core.shared.UserFeed;

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
  protected ResourceProxy<T> proxy;

  @Inject
  ResourceManager resourceManager;

  private boolean initialized = false;

  private ValueVisitor<T> visitor = new ValueVisitor<T>();

  @Override
  public T getResource() {
    return getDriver().flush();
  }

  @Override
  public void setResource(final T resource) {
    ensureInitialized();
    getDriver().edit(resource);
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
    proxy.execute(method, resource, new com.google.gwt.core.client.Callback<T, Throwable>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final T result) {
        resourceManager.goTo(getResource().getParent().getRendition(UserFeed.LIST));
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
