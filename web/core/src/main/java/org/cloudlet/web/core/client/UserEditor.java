package org.cloudlet.web.core.client;

import com.google.inject.Inject;

public class UserEditor extends AbstractUserFieldView {

  @Inject
  public UserEditor() {
    super("User Modify");
  }

}
