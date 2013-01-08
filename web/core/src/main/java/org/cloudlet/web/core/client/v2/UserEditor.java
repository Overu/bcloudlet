package org.cloudlet.web.core.client.v2;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.IsField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class UserEditor extends ContentPanel implements Editor<Resource>, TakesResource {

  @Inject
  ResourceManager resourceManager;

  private boolean initialized = false;

  private Resource resource;

  @Inject
  TextField path;

  @Inject
  @Ignore
  TextField email;

  @Inject
  TextField title;

  @Inject
  @Ignore
  TextField state;

  @Inject
  @Ignore
  TextField zip;

  public UserEditor() {
    setTitle("新建用户");
  }

  public void edit() {
    for (IsField field : FormPanelHelper.getFields(this)) {
      if (field instanceof Component) {
        Component comp = (Component) field;
        String path = comp.getData("path");
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
        String path = comp.getData("path");
        Object value = field.getValue();
        resource.setValue(path, value);
      }
    }
    return this.resource;
  }

  @Override
  public Resource getValue() {
    return resource;
  }

  @Override
  public void setValue(Resource resource) {
    this.resource = resource;
    ensureInitialized();
    edit();
  }

  protected void initView() {
    addButton(new TextButton("Save", new SelectHandler() {
      @Override
      public void onSelect(final SelectEvent event) {
        flush();
        save(resource);
      }
    }));

    setHeadingText("修改用户");

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    path.setAllowBlank(false);
    path.setData("path", "path");
    p.add(new FieldLabel(path, "Name"), new VerticalLayoutData(1, -1));

    email.setAllowBlank(false);
    email.setData("path", "email");
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));

    p.add(new FieldLabel(title, "Phone"), new VerticalLayoutData(1, -1));

    p.add(new FieldLabel(state, "State"), new VerticalLayoutData(1, -1));

    p.add(new FieldLabel(zip, "Zip"), new VerticalLayoutData(1, -1));
    setWidget(p);
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
        Resource listView = result.getRendition("_list");
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
