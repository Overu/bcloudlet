package org.cloudlet.web.core.shared;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

@Path("users")
public interface UserService extends Service<User, UsersFeed> {

	@Path("{id}")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	User getEntry(@PathParam("path") String path);

	User findUserByUsername(final String userName);

	@RequiresAuthentication
	void updatePassword(final String userName, final String newPwd);

	@GET
	@Produces({ MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_JSON })
	UsersFeed getFeed();
}
