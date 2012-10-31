package org.cloudlet.web.core.server;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.internal.process.MappableException;

public class JpaExceptionMapper implements
		ExceptionMapper<PersistenceException> {

	@Override
	public Response toResponse(PersistenceException exception) {
		if (exception instanceof NoResultException) {
			return Response.status(Status.NOT_FOUND).build();
		}

		throw new MappableException(exception);
	}
}
