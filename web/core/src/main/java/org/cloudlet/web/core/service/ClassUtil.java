/*
 * Copyright 2012 Retechcorp.com
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
package org.cloudlet.web.core.service;

import java.util.logging.Logger;

public class ClassUtil {

  static Logger logger = Logger.getLogger(ClassUtil.class.getName());

  public static <T> T createInstance(String className, Class<T> baseType) {
    try {
      Class cls = Class.forName(className);
      if (baseType.isAssignableFrom(cls)) {
        return (T) cls.newInstance();
      }
    } catch (Exception e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  public static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  public static String getSimpleName(Class<?> cls) {
    String name = cls.getName();
    int index = name.lastIndexOf(".");
    return index > 0 ? name.substring(index + 1) : name;
  }

  public static <T> T newInstance(Class<T> cls) {
    try {
      return cls.newInstance();
    } catch (Exception e) {
      logger.severe(e.getMessage());
      return null;
    }
  }

}
