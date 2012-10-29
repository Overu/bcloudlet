package org.cloudlet.web.core.shared;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

public interface Service<E extends Entry, F extends Feed<E>> {

	Entry getContainer();

	void setContainer(Entry entry);

	String getPath();

	void setPath(String path);

	@GET
	@Path("{path}")
	@Consumes(MediaType.WILDCARD)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	E getEntry(@PathParam("path") String path);

	F getFeed();

	@GET
	F getFeed(@PathParam("start") @DefaultValue("0") int start,
			@PathParam("limit") @DefaultValue("10") int limit);

	@DELETE
	@Path("{path}")
	void deleteEntry(@PathParam("path") String path);

	void deleteEntry(final E entry);

	@POST
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.WILDCARD)
	E createEntry(final E entry);

	@PUT
	@Path("{path}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.WILDCARD)
	E updateEntry(@PathParam("path") String path, JSONObject json);

	@Path("{path}/{subPath}")
	Service<?, ?> getService(@PathParam("path") String path,
			@PathParam("subPath") String servicePath);
}
