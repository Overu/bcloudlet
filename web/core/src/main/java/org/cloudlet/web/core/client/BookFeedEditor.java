package org.cloudlet.web.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.cloudlet.web.core.Book;
import org.cloudlet.web.core.BookFeed;

import java.util.List;

public class BookFeedEditor extends ResourceEditor<BookFeed> {

  interface Driver extends SimpleBeanEditorDriver<BookFeed, BookFeedEditor> {
  }

  public static final String NEW = "new";

  @Inject
  private Provider<BookEditor> editorProvider;

  private static Driver driver = GWT.create(Driver.class);

  @Override
  public Class<BookFeed> getResourceType() {
    return BookFeed.class;
  }

  @Override
  public void setPlace(ResourcePlace<BookFeed> place) {
    super.setPlace(place);
    place.resolve(BookFeed.class, new AsyncCallback<ResourcePlace<BookFeed>>() {
      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
      }

      @Override
      public void onSuccess(ResourcePlace<BookFeed> result) {
        createEditors();
      }
    });

  }

  @SuppressWarnings("unchecked")
  @Override
  protected Driver getDriver() {
    return driver;
  }

  @Override
  protected void initView() {
    setHeadingText("上传新书");
    // hide save button
    // super.initView();
    driver.initialize(this);
  }

  private void createEditors() {
    List<Book> entries = getPlace().getResource().getEntries();
    if (getPlace().getResource().getEntries() != null) {
      clear();
      for (Book user : entries) {
        BookEditor editor = editorProvider.get();
        editor.setHeaderVisible(false);
        editor.setBorders(false);
        ResourcePlace<Book> userPlace = getPlace().getChild(user.getPath());
        userPlace.setResource(user);
        editor.setPlace(userPlace);
        add(editor);
      }
    }
  }
}
