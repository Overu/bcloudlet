package org.cloudlet.web.core.service;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.persistence.NoResultException;

import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupService;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserService;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.google.inject.Inject;

public class GroupServiceTest extends WebTest {

	@Inject
	private GroupService groupService;

	@Inject
	CoreResourceConfig config;

	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		return config;
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		// groupService = WebResourceFactory.newResource(GroupService.class,
		// target());
	}

	@Test
	public void testSubResource() {
		System.out.println(UUID.randomUUID().toString());
		Group group;
		try {
			group = groupService.getEntry("mygroup");
		} catch (NoResultException e) {
			group = new Group();
			group.setPath("mygroup");
			groupService.createEntry(group);
		}
		UserService userService = groupService.getUserService(group.getPath());
		UserFeed users = userService.getFeed(0, 0);
		long total = users.getTotalResults();
		User user = new User();
		long r = total + 1;
		user.setName("Fan" + r);
		user.setEmail("fantongx@gmail.com");
		user.setPhone(Long.toString(r));
		userService.createEntry(user);
		users = userService.getFeed(0, 0);
		assertEquals(total + 1, users.getTotalResults());
	}

}
