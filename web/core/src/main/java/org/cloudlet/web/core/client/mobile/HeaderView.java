package org.cloudlet.web.core.client.mobile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HeaderView extends Composite {

  interface Binder extends UiBinder<Widget, HeaderView> {
  }

  private static Binder binder = GWT.create(Binder.class);

  public HeaderView() {
    initWidget(binder.createAndBindUi(this));
  }

}
