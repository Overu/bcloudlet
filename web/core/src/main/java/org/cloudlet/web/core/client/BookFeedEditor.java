package org.cloudlet.web.core.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.Feed;

import java.util.List;

public class BookFeedEditor extends ResourceEditor {

  public static final String NEW = "new";

  @Inject
  private Provider<BookEditor> editorProvider;

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
    setHeadingText("上传新书");
    // hide save button
    super.initView();
  }

  private void createEditors() {
    Resource feed = getValue();
    List<Resource> entries = feed.getList(Feed.ENTRIES);
    if (entries != null) {
      clear();
      for (Resource entry : entries) {
        BookEditor editor = editorProvider.get();
        editor.setHeaderVisible(false);
        editor.setBorders(false);
        editor.setValue(entry);
        add(editor);
      }
    }
  }
}
