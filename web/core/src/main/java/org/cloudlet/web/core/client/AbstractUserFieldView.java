package org.cloudlet.web.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.IsWidget;
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
import org.cloudlet.web.core.shared.WebView;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class AbstractUserFieldView extends WebView implements IsWidget, EntryPoint {

  interface Responsecallback {
    void completed(String text);
  }

  @Inject
  ResourceManager placeManager;

  private ContentPanel cp;

  protected Map<String, TextField> textFieldMap;

  protected TextField name;
  protected TextField email;
  protected TextField phone;
  protected TextField state;
  protected TextField zip;

  public AbstractUserFieldView(final String viewName) {
    textFieldMap = new HashMap<String, TextField>();

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    name = new TextField();
    name.setAllowBlank(false);
    p.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));
    textFieldMap.put("name", name);

    email = new TextField();
    email.setAllowBlank(false);
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));
    textFieldMap.put("email", email);

    phone = new TextField();
    p.add(new FieldLabel(phone, "Phone"), new VerticalLayoutData(1, -1));
    textFieldMap.put("phone", phone);

    state = new TextField();
    p.add(new FieldLabel(state, "State"), new VerticalLayoutData(1, -1));
    textFieldMap.put("state", state);

    zip = new TextField();
    p.add(new FieldLabel(zip, "Zip"), new VerticalLayoutData(1, -1));
    textFieldMap.put("zip", zip);

    cp = new ContentPanel();
    cp.setHeadingText(viewName);
    cp.setWidget(p);
    cp.addButton(new TextButton("Commit", new SelectHandler() {

      @Override
      public void onSelect(final SelectEvent event) {
        if (name.getText() == null || email.getText() == null) {
          Info.display("name or email is Null", "");
          return;
        }
        send(selectHandler(event));
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
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  protected JSONObject initJSON(final String text, final boolean isPut) {
    Set<Entry<String, TextField>> entrySet = textFieldMap.entrySet();
    JSONObject object = null;
    if (isPut) {
      object = JSONParser.parseLenient(text).isObject();
      object = object.get("dataGraph").isObject().get("root").isObject();
    } else {
      object = new JSONObject();
      putJSON(object, "@xsi.type", text);
    }
    for (Entry<String, TextField> entry : entrySet) {
      String key = entry.getKey();
      TextField textField = entry.getValue();
      if (isPut) {
        if (object.containsKey(key)) {
          textField.setText(object.get(key).isString().stringValue());
        }
      } else {
        String value = textField.getValue();
        if (value == null || value.equals("")) {
          continue;
        }
        putJSON(object, key, value);
      }
    }
    return object;
  }

  protected RequestBuilder initRequestBuilder(final Method httpMethod, final String url,
      final String header, final String value, final Responsecallback callback) {
    RequestBuilder request = new RequestBuilder(httpMethod, url);
    request.setHeader(header, value);
    request.setCallback(new RequestCallback() {
      @Override
      public void onError(final Request request, final Throwable exception) {
      }

      @Override
      public void onResponseReceived(final Request request, final Response response) {
        if (response.getStatusCode() != Response.SC_OK || callback == null) {
          return;
        }
        callback.completed(response.getText());
      }
    });
    return request;
  }

  @SuppressWarnings("unused")
  protected void onAttach(final AttachEvent event) {

  }

  protected abstract RequestBuilder selectHandler(SelectEvent event);

  protected void send(final RequestBuilder builder) {
    send(builder, "{\"dataGraph\":{\"root\":" + initJSON("user", false).toString() + "}}");
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
