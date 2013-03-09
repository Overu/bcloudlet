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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

  public static UUID parseUUID(String id) {
    if (id.length() == 32) {
      String[] components = new String[5];
      components[0] = "0x" + id.substring(0, 8);
      components[1] = "0x" + id.substring(8, 12);
      components[2] = "0x" + id.substring(12, 16);
      components[3] = "0x" + id.substring(16, 20);
      components[4] = "0x" + id.substring(20);
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
    return UUID.fromString(id);
  }

  public static String randomID() {
    UUID id = UUID.randomUUID();
    return id.toString().replaceAll("-", "");
  }

  /**
   * Performs URL decoding from UTF-8, trapping the UnsupportedEncodingException, which in practice will not be a problem.
   * 
   * @param s the string to decoded.
   * @return s decoded from utf-8.
   */
  public static String toURLDecoded(String s) {
    try {
      return URLDecoder.decode(s, "utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Performs URL encoding to UTF-8, trapping the UnsupportedEncodingException which in practice should never be thrown. Will correctly
   * encode the string to the URL encoding (URLEncoder.encode is for HTML form encoding, not for URL encoding) including using %20 instead
   * of + for space.
   * 
   * @param s the string to encode.
   * @return s encoded in UTF-8.
   */
  public static String toURLEncoded(String s) {
    if (s == null) {
      return null;
    }

    try {
      String encodedString = URLEncoder.encode(s, "UTF-8");

      // Goal is to replace all + with %20 efficiently. replaceAll on a pre-compiled pattern is extremely slow for these scenarios

      // Lazily allocate
      StringBuilder sb = null;

      for (int i = 0; i < encodedString.length(); i++) {
        char ch = encodedString.charAt(i);

        if (sb == null) {
          if (ch == '+') {
            sb = new StringBuilder((int) (encodedString.length() * 1.2)).append(encodedString.substring(0, i)).append("%20");
          }
        } else {
          if (ch == '+') {
            sb.append("%20");
          } else {
            sb.append(ch);
          }
        }
      }

      return (sb == null ? encodedString : sb.toString());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }

  public static final String PREFIX = "Core";
}
