package org.cloudlet.web.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
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

  public AbstractUserFieldView(final String viewName) {
    textFieldMap = new HashMap<String, TextField>();

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));

    final TextField name = new TextField();
    name.setAllowBlank(false);
    p.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));
    textFieldMap.put("name", name);

    final TextField email = new TextField();
    email.setAllowBlank(false);
    p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));
    textFieldMap.put("email", email);

    TextField phone = new TextField();
    p.add(new FieldLabel(phone, "Phone"), new VerticalLayoutData(1, -1));
    textFieldMap.put("phone", phone);

    TextField state = new TextField();
    p.add(new FieldLabel(state, "State"), new VerticalLayoutData(1, -1));
    textFieldMap.put("state", state);

    TextField zip = new TextField();
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
        selectHandler(event);
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

  protected JSONObject initJSON(JSONObject json) {
    Set<Entry<String, TextField>> entrySet = textFieldMap.entrySet();
    JSONObject object = null;
    if (json != null) {
      object = json;
    } else {
      object = new JSONObject();
    }
    for (Entry<String, TextField> entry : entrySet) {
      String key = entry.getKey();
      TextField textField = entry.getValue();
      if (json != null) {
        if (object.containsKey(key)) {
          textField.setText(object.get(key).toString());
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

  @SuppressWarnings("unused")
  protected void onAttach(final AttachEvent event) {

  }

  protected abstract void selectHandler(SelectEvent event);

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
