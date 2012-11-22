package org.cloudlet.web.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import org.cloudlet.web.core.shared.ResourceManager;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebView;

public abstract class AbstractUserFieldView extends WebView<User> implements EntryPoint,
    Editor<User> {

  interface Driver extends SimpleBeanEditorDriver<User, AbstractUserFieldView> {
  }

  interface Responsecallback {
    void completed(String text);
  }

  @Inject
  ResourceProxy<User> proxy;

  @Inject
  ResourceManager resourceManager;

  private ContentPanel cp;

  @Path(User.NAME)
  TextField name;
  @Path(User.EMAIL)
  TextField email;
  @Path(User.PHONE)
  TextField phone;
  @Path(User.STATE)
  TextField state;
  @Path(User.ZIP)
  TextField zip;

  private static Driver driver = GWT.create(Driver.class);

  public AbstractUserFieldView(final String viewName) {
    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    name = new TextField();
    name.setAllowBlank(false);
    p.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));

    email = new TextField();
    email.setAllowBlank(false);
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));

    phone = new TextField();
    p.add(new FieldLabel(phone, "Phone"), new VerticalLayoutData(1, -1));

    state = new TextField();
    p.add(new FieldLabel(state, "State"), new VerticalLayoutData(1, -1));

    zip = new TextField();
    p.add(new FieldLabel(zip, "Zip"), new VerticalLayoutData(1, -1));

    cp = new ContentPanel();
    cp.setHeadingText(viewName);
    cp.setWidget(p);
    cp.addButton(new TextButton("Save", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (name.getText() == null || email.getText() == null) {
          Info.display("name or email is Null", "");
          return;
        }
        saveResource(driver.flush());
      }
    }));

    cp.addAttachHandler(new Handler() {

      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          return;
        }
        onAttach(event);
      }
    });

    driver.initialize(this);
    driver.edit(resource);
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  @Override
  public void setValue(final User resource) {
    super.setValue(resource);
    initForm(resource);
  }

  protected void initForm(final User user) {
    driver.edit(user);
    // name.setText(user.getName());
    // email.setText(user.getEmail());
    // phone.setText(user.getPhone());
    // state.setText(user.getState());
    // zip.setText(user.getZip());
  }

  @SuppressWarnings("unused")
  protected void onAttach(final AttachEvent event) {

  }

  protected void saveResource(final User resource) {
    proxy.put(resource, new com.google.gwt.core.client.Callback<User, Throwable>() {
      @Override
      public void onFailure(final Throwable reason) {
      }

      @Override
      public void onSuccess(final User result) {
        resourceManager.goTo(getValue().getParent().getRendition(UserFeed.LIST));
      }
    });
  }

  protected void send(final RequestBuilder builder, final String requestData) {
    try {
      if (requestData != null) {
        builder.setRequestData(requestData);
      }
      builder.send();
    } catch (RequestException e) {
      e.printStackTrace();
    }
  }

  private void putJSON(final JSONObject object, final String key, final String text) {
    object.put(key, new JSONString(text));
  }
}
