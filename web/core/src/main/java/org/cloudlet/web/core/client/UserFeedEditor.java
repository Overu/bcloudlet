package org.cloudlet.web.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.shared.ResourcePlace;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;

import java.util.List;

public class UserFeedEditor extends ResourceEditor<UserFeed> {

  interface Driver extends SimpleBeanEditorDriver<UserFeed, UserFeedEditor> {
  }

  public static final String NEW = "new";

  @Inject
  private Provider<UserEditor> editorProvider;

  private static Driver driver = GWT.create(Driver.class);

  @Override
  public void setPlace(ResourcePlace<UserFeed> resource) {
    super.setPlace(resource);
    if (resource.getResource().getEntries() == null) {
      resource.load(new AsyncCallback<ResourcePlace<UserFeed>>() {
        @Override
        public void onFailure(final Throwable reason) {
        }

        @Override
        public void onSuccess(final ResourcePlace<UserFeed> data) {
          setPlace(data);
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
    List<User> entries = getPlace().getResource().getEntries();
    if (getPlace().getResource().getEntries() != null) {
      clear();
      for (User user : entries) {
        UserEditor editor = editorProvider.get();
        ResourcePlace<User> userPlace = getPlace().getChild(user.getPath());
        userPlace.setResource(user);
        editor.setPlace(userPlace);
        add(editor);
      }
    }
  }
}
