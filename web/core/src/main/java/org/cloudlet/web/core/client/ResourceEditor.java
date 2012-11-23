package org.cloudlet.web.core.client;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import org.cloudlet.web.core.shared.Resource;
import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebView;

public abstract class ResourceEditor<T extends Resource> extends WebView<T> implements Editor<T> {

  private boolean initialized = false;

  @Inject
  protected ResourceProxy<T> proxy;

  @Inject
  protected ContentPanel cp;

  @Inject
  ResourceManager resourceManager;

  @Override
  public Widget asWidget() {
    if (!initialized) {
      initView();
    }
    return cp;
  }

  @Override
  public void setValue(final T resource) {
    super.setValue(resource);
    initForm(resource);
  }

  protected abstract void initForm(T resource);

  protected void initView() {
    cp.addButton(new TextButton("Save", new SelectHandler() {
      @Override
      public void onSelect(final SelectEvent event) {
        if (validateForm()) {
          T resource = readForm();
          save(resource);
        }
      }
    }));
  }

  protected abstract T readForm();

  protected void save(final T resource) {
    RequestBuilder.Method method =
        resource.getId() == null ? RequestBuilder.POST : RequestBuilder.PUT;
    proxy.execute(method, resource, new com.google.gwt.core.client.Callback<T, Throwable>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final T result) {
        resourceManager.goTo(getValue().getParent().getRendition(UserFeed.LIST));
      }
    });
  }

  protected abstract boolean validateForm();
}
