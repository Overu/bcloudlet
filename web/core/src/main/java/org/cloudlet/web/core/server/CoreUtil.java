/*
 * Copyright 2012 Retechcorp.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.cloudlet.web.core.server;

import java.util.UUID;
import java.util.logging.Logger;

public class CoreUtil {

  static Logger logger = Logger.getLogger(CoreUtil.class.getName());

  public static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  public static UUID parseUUID(String name) {
    if (name.length() == 32) {
      String[] components = new String[5];
      components[0] = "0x" + name.substring(0, 8);
      components[1] = "0x" + name.substring(8, 12);
      components[2] = "0x" + name.substring(12, 16);
      components[3] = "0x" + name.substring(16, 20);
      components[4] = "0x" + name.substring(20);
      long mostSigBits = Long.decode(components[0]).longValue();
      mostSigBits <<= 16;
      mostSigBits |= Long.decode(components[1]).longValue();
      mostSigBits <<= 16;
      mostSigBits |= Long.decode(components[2]).longValue();

      long leastSigBits = Long.decode(components[3]).longValue();
      leastSigBits <<= 48;
      leastSigBits |= Long.decode(components[4]).longValue();

      return new UUID(mostSigBits, leastSigBits);
    }
    return UUID.fromString(name);
  }
}
