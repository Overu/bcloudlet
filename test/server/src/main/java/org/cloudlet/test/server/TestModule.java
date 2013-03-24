package org.cloudlet.test.server;

import com.google.common.testing.TearDown;
import com.google.common.testing.TearDownAccepter;
import com.google.guiceberry.GuiceBerryEnvMain;
import com.google.guiceberry.TestWrapper;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class TestModule extends AbstractModule {

  private static final class PersistServiceStarter implements GuiceBerryEnvMain {

    @Inject
    private PersistService persistService;

    @Override
    public void run() {
      // Starting a server should never be done in a @Provides method
      // (or inside Provider's get).
      persistService.start();
    }
  }

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Override
  protected void configure() {
    LogManager logManager = LogManager.getLogManager();
    try {
      URL url = LoggingUtil.searchLoggingFile();
      logManager.readConfiguration(url.openStream());
      logger.config("Config logging use " + url);
    } catch (Exception e) {
      System.err.println("TestingModule: Load logging configuration failed");
      System.err.println("" + e);
    }
    // super.configure();

    bind(GuiceBerryEnvMain.class).to(PersistServiceStarter.class);
  }

  @Provides
  TestWrapper testWrapperProvider(final TearDownAccepter tearDownAccepter, final UnitOfWork unitOfWork) {

    return new TestWrapper() {

      @Override
      public void toRunBeforeTest() {
        tearDownAccepter.addTearDown(new TearDown() {

          @Override
          public void tearDown() throws Exception {
            unitOfWork.end();
          }
        });
        unitOfWork.begin();
      }
    };
  }
}
