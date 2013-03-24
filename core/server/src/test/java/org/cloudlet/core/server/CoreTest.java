package org.cloudlet.core.server;

import com.google.inject.Inject;

import org.cloudlet.core.server.CoreResourceConfig;
import org.cloudlet.test.server.WebTest;
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
