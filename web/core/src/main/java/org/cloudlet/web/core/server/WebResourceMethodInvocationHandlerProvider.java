package org.cloudlet.web.core.server;

import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebResourceMethodInvocationHandlerProvider implements ResourceMethodInvocationHandlerProvider {

  private static class WebInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException,
        InvocationTargetException {
      if (target instanceof Content) {
        Content content = (Content) target;
        if (!(content instanceof Repository)) {
          content.initResource();
        }
      }
      return method.invoke(target, args);
    }
  }

  private static WebInvocationHandler handler = new WebInvocationHandler();

  @Override
  public InvocationHandler create(Invocable method) {
    return handler;
  }

}
