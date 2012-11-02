package org.cloudlet.web.core.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.cloudlet.web.core.shared.CoreTypes;

@Singleton
public class Launcher {
  @Inject
  public Launcher(UserGrid example) {
    CoreTypes.UserFeed.bind(ViewType.HOME_PAGE).toInstance(example);
  }
}