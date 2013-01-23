package org.cloudlet.web.core.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.shared.CorePackage;

import java.util.List;

public class UserFeedEditor extends ResourceEditor {

  @Inject
  private Provider<UserEditor> editorProvider;

  @Override
  public void setValue(Resource place) {
    super.setValue(place);
    place.load(new AsyncCallback<Resource>() {
      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
      }

      @Override
      public void onSuccess(Resource result) {
        createEditors();
      }
    });

  }

  @Override
  protected void initView() {
    setHeadingText("添加新用户");
    // hide save button
    super.initView();
  }

  private void createEditors() {
    Resource feed = getValue();
    List<Resource> entries = feed.getList(CorePackage.ENTRIES);
    if (entries != null) {
      clear();
      for (Resource entry : entries) {
        UserEditor editor = editorProvider.get();
        editor.setHeaderVisible(false);
        editor.setBorders(false);
        editor.setValue(entry);
        add(editor);
      }
    }
  }
}
