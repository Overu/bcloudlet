package org.cloudlet.web.core.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.User;

@Path("users")
public interface UserService extends ContentService<User> {

	@GET
	@Produces({ MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON })
	public Feed<User> getFeed();

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public User getById(@PathParam("id") String id);

}
