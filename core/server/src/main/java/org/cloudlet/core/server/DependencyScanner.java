package org.cloudlet.core.server;

import org.hibernate.ejb.packaging.JarVisitorFactory;
import org.hibernate.ejb.packaging.NativeScanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class DependencyScanner extends NativeScanner {

  public Class<? extends Repository> repoType;

  public DependencyScanner(Class<? extends Repository> repoType) {
    this.repoType = repoType;
  }

  @Override
  public Set<Class<?>> getClassesInJar(URL jartoScan, Set<Class<? extends Annotation>> annotationsToLookFor) {
    Set<Class<?>> classes = super.getClassesInJar(jartoScan, annotationsToLookFor);
    Set<Class<?>> visited = new HashSet<Class<?>>();
    Class cls = repoType;
    while (!Object.class.equals(cls)) {
      String entry = cls.getName().replaceAll("\\.", "/") + ".class";
      try {
        Enumeration<URL> xmls = Thread.currentThread().getContextClassLoader().getResources(entry);
        while (xmls.hasMoreElements()) {
          URL url = xmls.nextElement();
          URL jarUrl = JarVisitorFactory.getJarURLFromURLEntry(url, entry);
          Set<Class<?>> dpClasses = super.getClassesInJar(jarUrl, annotationsToLookFor);
          classes.addAll(dpClasses);
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      cls = cls.getSuperclass();
    }
    // visit(repoType, classes, visited, annotationsToLookFor);
    // visit(Reference.class, classes, visited, annotationsToLookFor);
    return classes;
  }

  private boolean isAnnotated(Class<?> cls, Set<Class<? extends Annotation>> annotationsToLookFor) {
    boolean flag = false;
    for (Class<? extends Annotation> annoType : annotationsToLookFor) {
      if (cls.getAnnotation(annoType) != null) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  private void visit(Class<?> cls, Set<Class<?>> found, Set<Class<?>> visited, Set<Class<? extends Annotation>> annotationsToLookFor) {
    if (cls == null || Object.class.equals(cls) || visited.contains(cls)) {
      return;
    }
    visited.add(cls);
    if (isAnnotated(cls, annotationsToLookFor)) {
      found.add(cls);
      for (Field f : cls.getDeclaredFields()) {
        visit(f.getType(), found, visited, annotationsToLookFor);
      }
      visit(cls.getSuperclass(), found, visited, annotationsToLookFor);
    }
  }
}
