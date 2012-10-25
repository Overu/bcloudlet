package org.cloudlet.web.core.service;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.User;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

public class GroupServiceTest extends JerseyTest {

	private GroupService rootGroupService;

	@Override
	protected ResourceConfig configure() {
		// mvn test
		// -DargLine="-Djersey.config.test.container.factory=org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory"
		// mvn test
		// -DargLine="-Djersey.config.test.container.factory=org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory"
		// mvn test
		// -DargLine="-Djersey.config.test.container.factory=org.glassfish.jersey.test.jdkhttp.JdkHttpServerTestContainerFactory"
		enable(TestProperties.LOG_TRAFFIC);
		// enable(TestProperties.DUMP_ENTITY);
		return CoreApplication.createApp();
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
