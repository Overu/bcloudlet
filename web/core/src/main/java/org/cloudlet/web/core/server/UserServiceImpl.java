package org.cloudlet.web.core.server;

import java.util.ArrayList;
import java.util.List;

import org.cloudlet.web.core.service.UserService;
import org.cloudlet.web.core.shared.Content;
import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.User;

public class UserServiceImpl implements UserService {

	Content container;

	public UserServiceImpl() {
	}

	public UserServiceImpl(Content container) {
		this.container = container;
	}

	@Override
	public User getById(String id) {
		User bean = new User();
		if (container == null)
			bean.setId(id);
		else
			bean.setId(id + "@" + container.getId());
		return bean;
	}

	@Override
	public Feed<User> getFeed() {
		Feed<User> feed = new Feed<User>();
		List<User> users = new ArrayList<User>();
		User u1 = new User();
		u1.setId("jack");
		u1.setName("Jack");
		u1.setEmail("jack@gmail.com");
		u1.setPhone("+86-13911231234");
		users.add(u1);
		User u2 = new User();
		u2.setId("rose");
		u2.setName("Rose");
		u2.setEmail("rose@gmail.com");
		u2.setPhone("13812834321");
		users.add(u2);
		feed.setEntries(users);
		feed.setTotalResults(20);
		return feed;
	}
}
