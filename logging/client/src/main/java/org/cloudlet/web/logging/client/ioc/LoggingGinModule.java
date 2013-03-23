/*
 * Copyright 2012 Goodow.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cloudlet.web.logging.client.ioc;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.cloudlet.web.logging.client.LogHandler;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LoggingGinModule extends AbstractGinModule {
  @Singleton
  public static class Binder {

    private static final Logger wireLogger = Logger.getLogger("WireActivityLogger");

    @Inject
    public Binder(final LogHandler logHandler
    // , final ChannelContextProvider channelContextProvider
    // , final LoggingRequestProvider loggingRequestProvider
    ) {
      logger.info("LoggingGinModule start");

      Logger rootLogger = Logger.getLogger("");
      rootLogger.addHandler(logHandler);
      ArrayList<String> ignoredLoggerNames = new ArrayList<String>();
      // ignoredLoggerNames.add(LoggingGinModule.class.getName());

      // rootLogger.addHandler(new RequestFactoryLogHandler(loggingRequestProvider, Level.WARNING,
      // ignoredLoggerNames));

      // if (GWT.isProdMode() && Connectivity.isOnline()) {
      // if (false) {
      // logger.finest("request token");
      // channelContextProvider.channelContext().getToken("logging." + new Date().toString()).fire(
      // new Receiver<String>() {
      //
      // @Override
      // public void onSuccess(final String response) {
      // openChannel(response);
      // }
      // });
      // }

      logger.finest("EagerSingleton end");
    }

  }

  private static final Logger logger = Logger.getLogger(LoggingGinModule.class.getName());

  @Override
  protected void configure() {
    bind(Binder.class).asEagerSingleton();
  }

}
