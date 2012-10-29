package org.cloudlet.web.core.server;

import java.util.List;

import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupService;
import org.cloudlet.web.core.shared.GroupsFeed;
import org.cloudlet.web.core.shared.Service;
import org.cloudlet.web.core.shared.UserService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class GroupServiceImpl extends ServiceImpl<Group, GroupsFeed> implements
		GroupService {

	public GroupServiceImpl() {
	}

	@Transactional
	public List<Group> createGroups(List<Group> groups) {
		for (Group group : groups) {
			createEntry(group);
		}
		return groups;
	}

	@Inject
	Provider<UserServiceImpl> userServiceProvider;

	public UserService getUserService(String groupId) {
		Group g = getEntry(groupId);
		UserService service = userServiceProvider.get();
		service.setContainer(g);
		service.setPath("users");
		return service;
	}

	@Override
	protected Class<Group> getEntryType() {
		return Group.class;
	}

	@Override
	protected Class<GroupsFeed> getFeedType() {
		return GroupsFeed.class;
	}

	@Override
	protected Class<? extends Service<Group, GroupsFeed>> getServiceType() {
		return GroupService.class;
	}

}
