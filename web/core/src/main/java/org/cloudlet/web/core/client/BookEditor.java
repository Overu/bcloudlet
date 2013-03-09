package org.cloudlet.web.core.client;

import com.google.inject.Inject;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextField;

import org.cloudlet.web.core.server.Book;
import org.cloudlet.web.core.server.Content;

public class BookEditor extends ResourceEditor {

  @Inject
  FormPanel form;

  // @Inject
  // @Ignore
  // TextField resourceType;

  @Inject
  TextField title;

  @Inject
  MediaField source;

  @Inject
  ImageField cover;

  @Override
  protected void initView() {
    super.initView();
    setHeadingText("编辑图书");

    VerticalLayoutContainer p = new VerticalLayoutContainer();

    // TODO hide resourceType
    // resourceType.setName(Resource.RESOURCE_TYPE);
    // p.add(resourceType);

    source.setName(Book.SOURCE);
    p.add(new FieldLabel(source, "来源"), new VerticalLayoutData(1, -1, new Margins(10, 0, 10, 10)));

    cover.setName(Book.COVER);
    p.add(new FieldLabel(cover, "封面"), new VerticalLayoutData(1, -1, new Margins(10, 0, 10, 10)));

    title.setName(Content.TITLE);
    title.setAllowBlank(false);
    p.add(new FieldLabel(title, "Title"), new VerticalLayoutData(500, -1, new Margins(10, 0, 10, 10)));

    form.add(p);
    form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
      @Override
      public void onSubmitComplete(SubmitCompleteEvent event) {
        String html = event.getResults();
        int begin = html.indexOf(">");
        int end = html.lastIndexOf("<");
        String json = html.substring(begin + 1, end);
        Resource result = getValue();
        result.read(json);
        Resource place = result.getParent();
        resourceManager.goTo(place);
      }
    });

    form.setEncoding(Encoding.MULTIPART);
    setWidget(form);
    setBodyBorder(false);
  }

  @Override
  protected void save(Resource book) {
    form.setMethod(Method.POST);
    StringBuilder uri;
    if (book.getId() == null) {
      uri = book.getParent().getUriBuilder();
    } else {
      uri = book.getUriBuilder();
    }
    uri.insert(0, "api");
    form.setAction(uri.toString());
    form.submit();
  }
}
