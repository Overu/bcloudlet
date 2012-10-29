package org.cloudlet.web.core.shared;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("groups")
public interface GroupService extends Service<Group, GroupsFeed> {

	@POST
	@Consumes({ MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML })
	List<Group> createGroups(List<Group> entity);

	@GET
	@Path("{path}")
	@Consumes(MediaType.WILDCARD)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	Group getEntry(@PathParam("path") String path);

	@Path("{path}/users")
	UserService getUserService(@PathParam("path") String path);

}
