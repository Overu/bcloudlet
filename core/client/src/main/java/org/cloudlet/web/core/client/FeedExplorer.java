package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

public abstract class FeedExplorer extends BorderLayoutContainer implements TakesResource, WidgetContainer {

  private Resource resource;

  @Inject
  ResourceManager resourceManager;

  private IsWidget defaultWidget;

  @Override
  public void acceptWidget(IsWidget presenter) {
    setWidget(presenter);
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource value) {
    this.resource = value;
    ensureInitialized();
    if (resource == resourceManager.getWhere()) {
      if (defaultWidget == null) {
        defaultWidget = createDefaultWidget();
      }
      acceptWidget(defaultWidget);
      if (defaultWidget instanceof TakesResource) {
        ((TakesResource) defaultWidget).setValue(resource);
      }
    }
  }

  protected abstract IsWidget createDefaultWidget();

  protected void initView() {
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  private void ensureInitialized() {
    if (!isOrWasAttached()) {
      initView();
    }
  }

}
