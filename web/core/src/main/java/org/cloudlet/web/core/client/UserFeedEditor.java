package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import com.sencha.gxt.widget.core.client.container.SimpleContainer;

import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.WebView;

public class UserFeedEditor extends WebView<UserFeed> {

  @Inject
  private Provider<UserEditor> editorProvider;

  @Inject
  ResourceProxy<UserFeed> proxy;

  private SimpleContainer cp;

  public UserFeedEditor() {
    cp = new SimpleContainer();
  }

  @Override
  public Widget asWidget() {
    return cp;
  }

  @Override
  public void setValue(final UserFeed resource) {
    super.setValue(resource);
    if (resource.getEntries() == null) {
      proxy.load(resource, new Callback<UserFeed, Throwable>() {
        @Override
        public void onFailure(final Throwable reason) {
        }

        @Override
        public void onSuccess(final UserFeed data) {
          setValue(data);
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
