package org.cloudlet.web.core.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;

public class UserFeedEditor extends ResourceEditor<UserFeed> {

  interface Driver extends SimpleBeanEditorDriver<UserFeed, UserFeedEditor> {
  }

  @Inject
  private Provider<UserEditor> editorProvider;

  private static Driver driver = GWT.create(Driver.class);

  @Override
  public void setResource(final UserFeed resource) {
    super.setResource(resource);
    if (resource.getEntries() == null) {
      proxy.load(resource, new Callback<UserFeed, Throwable>() {
        @Override
        public void onFailure(final Throwable reason) {
        }

        @Override
        public void onSuccess(final UserFeed data) {
          setResource(data);
        }
      });
    } else {
      createEditors();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Driver getDriver() {
    return driver;
  }

  @Override
  protected void initView() {
    super.initView();
    driver.initialize(this);
  }

  private void createEditors() {
    if (getResource().getEntries() != null) {
      clear();
      for (User user : getResource().getEntries()) {
        UserEditor editor = editorProvider.get();
        editor.setResource(user);
        add(editor);
      }
    }
  }
}
