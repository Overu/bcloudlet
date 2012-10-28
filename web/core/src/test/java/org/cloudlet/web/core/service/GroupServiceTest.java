package org.cloudlet.web.core.service;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupService;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserService;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.google.inject.Inject;

public class GroupServiceTest extends WebTest {

	private GroupService rootGroupService;

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
		rootGroupService = WebResourceFactory.newResource(GroupService.class,
				target());
	}

	@Test
	public void testGetIt() {
		assertEquals("Got it!", rootGroupService.getIt());
	}

	@Test
	public void testPostIt() {
		Group bean = new Group();
		bean.setName("Ahoj");
		assertEquals("Ahoj",
				rootGroupService.postIt(Collections.singletonList(bean)).get(0)
						.getName());
	}

	@Test
	public void testPathParam() {
		assertEquals("jouda", rootGroupService.getById("jouda").getName());
	}

	@Test
	public void testQueryParam() {
		assertEquals("jiri", rootGroupService.getByName("jiri"));
	}

	@Test
	public void testUserService() {
		Group group = rootGroupService.getById("root");
		UserService userService = rootGroupService
				.getUserService(group.getId());
		User u1 = userService.getById("user1");
		assertEquals("user1@root", u1.getId());
		assertEquals(20, userService.getFeed().getTotalResults());
	}
}
