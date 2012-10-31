package org.cloudlet.web.core.server;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Providers;

import org.glassfish.jersey.server.internal.process.MappableException;

public class ReflectExceptionMapper implements
		ExceptionMapper<InvocationTargetException> {

	private final Providers providers;

	/**
	 * Create new EJB exception mapper.
	 * 
	 * @param providers
	 *            JAX-RS Providers.
	 */
	public ReflectExceptionMapper(@Context Providers providers) {
		this.providers = providers;
	}

	@Override
	public Response toResponse(InvocationTargetException exception) {
		Throwable t = exception.getCause();
		if (t instanceof Exception) {
			final Exception cause = (Exception) t;
			if (cause != null) {
				final ExceptionMapper mapper = providers
						.getExceptionMapper(cause.getClass());
				if (mapper != null) {
					// noinspection unchecked
					return mapper.toResponse(cause);
				} else if (cause instanceof WebApplicationException) {
					return ((WebApplicationException) cause).getResponse();
				}
			}
		}

		// Re-throw so the exception can be passed through to the
		// servlet container
		throw new MappableException((t == null) ? exception : t);
	}

}
