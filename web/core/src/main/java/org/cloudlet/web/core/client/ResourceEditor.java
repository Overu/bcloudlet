package org.cloudlet.web.core.client;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.IsField;

import org.cloudlet.web.core.shared.CorePackage;

public class ResourceEditor extends ContentPanel implements Editor<Resource>, TakesResource {

  private boolean initialized = false;

  private Resource resource;

  @Inject
  ResourceManager resourceManager;

  public void edit() {
    for (IsField field : FormPanelHelper.getFields(this)) {
      if (field instanceof Component) {
        Component comp = (Component) field;
        String path = comp.getData(CorePackage.PATH);
        if (path != null) {
          String value = resource.getString(path);
          field.setValue(value);
        }
      }
    }
  }

  public Resource flush() {
    for (IsField field : FormPanelHelper.getFields(this)) {
      if (field instanceof Component) {
        Component comp = (Component) field;
        String path = getPath(comp);
        if (path != null) {
          Object value = field.getValue();
          resource.setValue(path, value);
        }
      }
    }
    return this.resource;
  }

  public String getPath(Component comp) {
    return comp.getData(CorePackage.PATH);
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  public void setPath(Component comp, String value) {
    comp.setData(CorePackage.PATH, value);
  }

  @Override
  public void setValue(Resource value) {
    resource = value;
    ensureInitialized();
  }

  protected void initView() {
    addButton(new TextButton("Save", new SelectHandler() {
      @Override
      public void onSelect(final SelectEvent event) {
        flush();
        save(resource);
      }
    }));

  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  protected void save(Resource place) {
    place.save(new AsyncCallback<Resource>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final Resource result) {
        Resource listView = result.getRendition(CorePackage.HOME);
        resourceManager.goTo(listView);
      }
    });
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }
}
