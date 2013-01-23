package org.cloudlet.web.core.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.core.client.util.Rectangle;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
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
    HBoxLayoutContainer p1 = new HBoxLayoutContainer();
    p1.setPadding(new Padding(5));
    p1.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);

    form.add(p1);

    p1.add(new HTML("Retech"));
    BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
    flex.setFlex(1);
    p1.add(new Label(), flex);

    final TextField userName = new TextField();
    userName.setAllowBlank(false);
    userName.setEmptyText("Enter your Username...");
    p1.add(new FieldLabel(userName, "Username"), new BoxLayoutData(new Margins(0, 15, 0, 0)));

    PasswordField passWord = new PasswordField();
    passWord.setAllowBlank(false);
    p1.add(new FieldLabel(passWord, "Password"), new BoxLayoutData(new Margins(0, 15, 0, 0)));

    p1.add(new TextButton("Log in", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        Info.display("Log in", userName.getText());
      }
    }), new BoxLayoutData(new Margins(0, 15, 0, 0)));
    p1.add(new TextButton("Reset", new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        form.reset();
      }
    }), new BoxLayoutData(new Margins(0, 15, 0, 0)));
    setWidget(form);
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
