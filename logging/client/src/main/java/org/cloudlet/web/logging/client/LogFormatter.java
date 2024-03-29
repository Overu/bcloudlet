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
package org.cloudlet.web.logging.client;

import com.google.gwt.logging.client.HtmlLogFormatter;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.logging.LogRecord;

@Singleton
public final class LogFormatter extends HtmlLogFormatter {

  @Inject
  public LogFormatter() {
    super(false);
  }

  @Override
  public String format(final LogRecord event) {
    StringBuilder html = new StringBuilder(getHtmlPrefix(event));
    html.append(getHtmlPrefix(event));
    html.append(event.getMessage());
    html.append(getHtmlSuffix(event));
    return html.toString();
  }

}
