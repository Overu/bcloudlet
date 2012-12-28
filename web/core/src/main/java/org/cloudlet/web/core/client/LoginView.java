package org.cloudlet.web.core.client;

import com.google.inject.Inject;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class LoginView extends SimpleContainer {

  private boolean initialized = false;

  @Inject
  FormPanel form;

  protected void initView() {
    HBoxLayoutContainer c = new HBoxLayoutContainer();
    c.setPack(BoxLayoutPack.CENTER);
    c.setSize("100%", "100%");

    ContentPanel cp = new ContentPanel();
    cp.setLayoutData(new BoxLayoutData(new Margins(100, 0, 0, 0)));
    cp.setHeadingText("Log in");
    cp.setWidth(350);
    cp.setBodyStyle("background: none; padding: 5px");

    FieldSet fieldSet = new FieldSet();
    fieldSet.setHeadingText("User Log in");
    fieldSet.setCollapsible(false);
    cp.add(fieldSet);

    VerticalLayoutContainer p = new VerticalLayoutContainer();

    form.add(p);
    fieldSet.add(form);

    final TextField userName = new TextField();
    userName.setAllowBlank(false);
    userName.setEmptyText("Enter your Username...");
    p.add(new FieldLabel(userName, "Username"), new VerticalLayoutData(1, -1));

    PasswordField passWord = new PasswordField();
    passWord.setAllowBlank(false);
    p.add(new FieldLabel(passWord, "Password"), new VerticalLayoutData(1, -1));

    cp.addButton(new TextButton("Log in", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        Info.display("Log in", userName.getText());
      }
    }));
    cp.addButton(new TextButton("Reset", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        form.reset();
      }
    }));

    c.add(cp);
    setWidget(c);
  }

  @Override
  protected void onAttach() {
    ensureInitialized();
    super.onAttach();
  }

  private void ensureInitialized() {
    if (!initialized) {
      initialized = true;
      initView();
    }
  }
}
