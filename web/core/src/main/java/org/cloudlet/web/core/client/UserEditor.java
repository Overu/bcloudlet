package org.cloudlet.web.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

import org.cloudlet.web.core.shared.User;

public class UserEditor extends ResourceEditor<User> {

  interface Driver extends SimpleBeanEditorDriver<User, UserEditor> {
  }

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

  @Inject
  public UserEditor() {
    super("User Modify");

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

    cp.setWidget(p);

    driver.initialize(this);
    driver.edit(resource);
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  protected void initForm(final User user) {
    driver.edit(user);
  }

  @Override
  protected User readForm() {
    return driver.flush();
  }

  @Override
  protected boolean validateForm() {
    if (!name.isValid() || !email.isValid()) {
      Info.display("name or email is Null", "");
      return false;
    }
    return true;
  }

}
