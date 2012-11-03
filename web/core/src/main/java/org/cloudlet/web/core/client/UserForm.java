package org.cloudlet.web.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.RequestFactory;

import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

import org.cloudlet.web.core.shared.WebPlaceManager;
import org.cloudlet.web.core.shared.WebView;

public class UserForm extends WebView implements IsWidget, EntryPoint {

  @Inject
  WebPlaceManager placeManager;

  @Override
  public Widget asWidget() {

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    final TextField name = new TextField();
    name.setAllowBlank(false);
    name.setName("name");
    p.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));

    final TextField email = new TextField();
    email.setAllowBlank(false);
    email.setName("email");
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));

    final TextField phone = new TextField();
    phone.setName("phone");
    p.add(new FieldLabel(phone, "phone"), new VerticalLayoutData(1, -1));

    final TextField state = new TextField();
    state.setName("state");
    p.add(new FieldLabel(state, "state"), new VerticalLayoutData(1, -1));

    final TextField zip = new TextField();
    zip.setName("zip");
    p.add(new FieldLabel(zip, "zip"), new VerticalLayoutData(1, -1));

    final FormPanel formPanel = new FormPanel();
    // formPanel.setAction("api/groups/mygroup/users");
    // formPanel.setMethod(Method.POST);
    // formPanel.setEncoding(Encoding.URLENCODED);
    formPanel.add(p);

    FramedPanel cp1 = new FramedPanel();
    cp1.setHeadingText("Form Panel");
    cp1.setCollapsible(true);
    cp1.setWidget(formPanel);
    cp1.setWidth(300);

    final RequestBuilder builder1 =
        new RequestBuilder(RequestBuilder.POST, "api/groups/mygroup/users");
    builder1.setHeader("Content-Type", RequestFactory.JSON_CONTENT_TYPE_UTF8);
    builder1.setCallback(new RequestCallback() {
      @Override
      public void onError(final Request request, final Throwable exception) {
      }

      @Override
      public void onResponseReceived(final Request request, final Response response) {
        placeManager.goTo(place, WebView.HOME);
      }

    });

    return cp1;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  private String getParameter(final String value) {
    return value == null ? "" : value;
  }

}
