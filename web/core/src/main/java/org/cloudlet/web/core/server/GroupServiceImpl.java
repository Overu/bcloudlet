package org.cloudlet.web.core.server;

import java.util.List;

import org.cloudlet.web.core.service.GroupService;
import org.cloudlet.web.core.service.UserService;
import org.cloudlet.web.core.shared.Group;

public class GroupServiceImpl implements GroupService {

	@Override
	public String getIt() {
		return "Got it!";
	}

	@Override
	public List<Group> postIt(List<Group> entity) {
		return entity;
	}

	@Override
	public Group getById(String id) {
		Group g = new Group();
		g.setId(id);
		g.setName(id);
		return g;
	}

	@Override
	public String getByName(String name) {
		return name;
	}

	@Override
	public UserService getUserService(String groupId) {
		Group g = getById(groupId);
		return new UserServiceImpl(g);
	}
}
