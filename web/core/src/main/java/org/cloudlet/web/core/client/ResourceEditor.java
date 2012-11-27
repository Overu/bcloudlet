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
import com.google.web.bindery.autobean.shared.AutoBeanVisitor;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import org.cloudlet.web.core.CoreAutoBeanFactory;
import org.cloudlet.web.core.Resource;

import java.util.Map;

public abstract class ResourceEditor<T extends Resource> extends ContentPanel implements Editor<T>, ResourceWidget<T> {

  class ValueVisitor<V> extends EditorVisitor {

    private V value;

    @Override
    public <M> boolean visit(final EditorContext<M> ctx) {
      M value = ctx.getFromModel();
      return super.visit(ctx);
    }
  }

  @Inject
  CoreAutoBeanFactory factory;

  @Inject
  protected ResourceProxy<ResourcePlace<T>> proxy;

  @Inject
  ResourceManager resourceManager;

  private boolean initialized = false;

  private ValueVisitor<T> visitor = new ValueVisitor<T>();

  private ResourcePlace<T> place;

  private AutoBean<T> bean1;
  private AutoBean<T> bean2;

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
    swapBean();
  }

  protected abstract <D extends SimpleBeanEditorDriver<T, ResourceEditor<T>>> D getDriver();

  protected void initView() {
    addButton(new TextButton("Save", new SelectHandler() {
      @Override
      public void onSelect(final SelectEvent event) {
        T resource = getDriver().flush();
        if (getDriver().isDirty()) {
          final Map<String, Object> diff = AutoBeanUtils.diff(bean2, bean1);
          bean2 = factory.create(bean1.getType());
          bean2.accept(new AutoBeanVisitor() {
            @Override
            public boolean visitValueProperty(final String propertyName, final Object value, final PropertyContext ctx) {
              if (diff.containsKey(propertyName)) {
                ctx.set(diff.get(propertyName));
              }
              return false;
            }
          });
          place.setResource(bean2.as());
        }
        save();
      }
    }));
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  protected void save() {
    T resource = getPlace().getResource();
    RequestBuilder.Method method = resource.getId() == null ? RequestBuilder.POST : RequestBuilder.PUT;
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

  private void swapBean() {
    bean1 = AutoBeanUtils.getAutoBean(place.getResource());
    final Map<String, Object> allProperties = AutoBeanUtils.getAllProperties(bean1);
    bean2 = factory.create(bean1.getType());
    bean2.accept(new AutoBeanVisitor() {
      @Override
      public boolean visitValueProperty(final String propertyName, final Object value, final PropertyContext ctx) {
        if (allProperties.containsKey(propertyName)) {
          Object object = allProperties.get(propertyName);
          if (object == null) {
            return false;
          }
          ctx.set(object);
        }
        return false;
      }
    });
  }

}
