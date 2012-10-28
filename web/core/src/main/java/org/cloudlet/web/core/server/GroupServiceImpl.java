package org.cloudlet.web.core.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupService;
import org.cloudlet.web.core.shared.UserService;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Path("groups")
public class GroupServiceImpl extends ContentServiceImpl<Group> implements
		GroupService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@POST
	@Consumes({ MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML })
	public List<Group> postIt(List<Group> entity) {
		return entity;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.WILDCARD)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Group getById(@PathParam("id") String id) {
		Group g = new Group();
		g.setId(id);
		g.setName(id);
		return g;
	}

	@Path("query")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getByName(@PathParam("name") String name) {
		return name;
	}

	@Inject
	Provider<UserService> userServiceProvider;

	@Path("{id}/users")
	public UserService getUserService(@PathParam("id") String groupId) {
		Group g = getById(groupId);
		UserService service = userServiceProvider.get();
		service.setContainer(g);
		return service;
	}
}
