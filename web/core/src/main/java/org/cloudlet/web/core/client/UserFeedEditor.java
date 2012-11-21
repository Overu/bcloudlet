package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import com.sencha.gxt.widget.core.client.ContentPanel;

import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebView;

import java.util.ArrayList;
import java.util.List;

public class UserFeedEditor extends WebView<UserFeed> {

  @Inject
  private Provider<UserEditor> editorProvider;

  @Inject
  ResourceProxy<UserFeed> proxy;

  private ContentPanel cp;

  public UserFeedEditor() {
    cp = new ContentPanel();
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  public void setValue(final UserFeed resource) {
    super.setValue(resource);
    if (resource.getEntries() == null) {
      proxy.load(resource, new Callback<String, Throwable>() {
        @Override
        public void onFailure(Throwable reason) {
        }

        @Override
        public void onSuccess(String data) {
          JSONObject dg = JSONParser.parseLenient(data).isObject();
          JSONObject root = dg.get("dataGraph").isObject().get("root").isObject();
          JSONValue c = root.get(Feed.ENTRIES);
          if (c != null) {
            List<User> entries = new ArrayList<User>();
            JSONArray children = c.isArray();
            if (children != null) {
              for (int i = 0; i < children.size(); i++) {
                JSONObject object = children.get(i).isObject();
                User child = (User) CoreClientModule.readResource(object);
                child.setParent(resource.getHome());
                entries.add(child);
              }
            } else {
              JSONObject object = c.isObject();
              User child = (User) CoreClientModule.readResource(object);
              child.setParent(resource.getHome());
              entries.add(child);
            }
            resource.setEntries(entries);
            createEditors();
          }
        }
      });
    } else {
      createEditors();
    }
  }

  private void createEditors() {
    if (resource.getEntries() != null) {
      cp.clear();
      for (User user : resource.getEntries()) {
        UserEditor editor = editorProvider.get();
        editor.setValue(user);
        cp.add(editor);
      }
    }
  }
}
