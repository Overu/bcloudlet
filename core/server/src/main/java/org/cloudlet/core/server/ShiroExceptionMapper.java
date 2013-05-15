package org.cloudlet.core.server;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.glassfish.jersey.server.internal.process.MappableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ShiroExceptionMapper implements ExceptionMapper<ShiroException> {

  @Override
  public Response toResponse(ShiroException exception) {
    if (exception instanceof AuthenticationException) {
      return Response.status(Status.UNAUTHORIZED).build();
    } else if (exception instanceof UnauthorizedException) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    throw new MappableException(exception);
  }
}
