package org.cloudlet.web.core.client;

import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

import org.cloudlet.web.core.server.Content;
import org.cloudlet.web.core.server.User;

public class UserEditor extends ResourceEditor {

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

  @Override
  protected void initView() {
    super.initView();
    setHeadingText("修改用户");

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    path.setAllowBlank(false);
    setPath(path, Content.PATH);
    p.add(new FieldLabel(path, "Name"), new VerticalLayoutData(1, -1));

    email.setAllowBlank(false);
    setPath(email, User.EMAIL);
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));

    setPath(title, Content.TITLE);
    p.add(new FieldLabel(title, "Phone"), new VerticalLayoutData(1, -1));

    setPath(state, User.STATE);
    p.add(new FieldLabel(state, "State"), new VerticalLayoutData(1, -1));

    setPath(zip, User.ZIP);
    p.add(new FieldLabel(zip, "Zip"), new VerticalLayoutData(1, -1));
    setWidget(p);
  }
}
