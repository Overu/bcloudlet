package org.cloudlet.web.core.server;

import com.google.inject.Inject;

import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;

public abstract class CoreTest extends WebTest {

  @Inject
  CoreResourceConfig config;

  @Override
  protected ResourceConfig configure() {
    enable(TestProperties.LOG_TRAFFIC);
    return config;
  }
}
