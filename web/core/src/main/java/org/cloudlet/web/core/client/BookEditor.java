package org.cloudlet.web.core.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.inject.Inject;

import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextField;

import org.cloudlet.web.core.Book;
import org.cloudlet.web.core.Resource;

public class BookEditor extends ResourceEditor<Book> {

  interface Driver extends SimpleBeanEditorDriver<Book, BookEditor> {
  }

  @Inject
  FormPanel form;

  @Inject
  TextField resourceType;

  @Inject
  TextField title;

  @Inject
  @Ignore
  FileUploadField source;

  private static Driver driver = GWT.create(Driver.class);

  @Override
  public Class<Book> getResourceType() {
    return Book.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Driver getDriver() {
    return driver;
  }

  @Override
  protected void initView() {
    super.initView();
    setHeadingText("修改用户");
    form.setEncoding(Encoding.MULTIPART);
    setWidget(form);

    VerticalLayoutContainer p = new VerticalLayoutContainer();
    p.setLayoutData(new MarginData(8));
    form.add(p);

    source.setName("source");
    source.setAllowBlank(false);
    p.add(new FieldLabel(source, "Source"));

    title.setName(Resource.TITLE);
    title.setAllowBlank(false);
    p.add(new FieldLabel(title, "Title"));

    // TODO hide resourceType
    resourceType.setName(Resource.RESOURCE_TYPE);
    p.add(resourceType);

    form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
      @Override
      public void onSubmitComplete(SubmitCompleteEvent event) {
        System.out.println(event.getResults());
      }
    });
  }

  @Override
  protected void save() {
    Book book = getPlace().getResource();
    form.setMethod(Method.POST);
    StringBuilder uri;
    if (book.getId() == null) {
      uri = getPlace().getParent().getUriBuilder();
    } else {
      uri = getPlace().getUriBuilder();
    }
    uri.insert(0, "api");
    form.setAction(uri.toString());
    form.submit();
  }

}
