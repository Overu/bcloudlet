package org.cloudlet.web.core.service;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.persistence.NoResultException;
import javax.ws.rs.NotFoundException;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.WebPlatform;
import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.google.inject.Inject;

public class GroupServiceTest extends WebTest {

	@Inject
	CoreResourceConfig config;

	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		return config;
	}

	@Test
	public void testSubResource() {
		System.out.println(UUID.randomUUID().toString());
		Group root = (Group) WebPlatform.getDefault().getRoot();
		Group group;
		group = root.getGroups().getChild("mygroup");
		if (group == null) {

		}
		try {
			group.load();
		} catch (NotFoundException e) {
			group.save();
		} catch (NoResultException e) {
			group.save();
		} catch (Exception e) {
			group.save();
		}
		UserFeed users = (UserFeed) group.getUsers().load();
		long total = users.getTotalResults();
		User user = new User();
		long r = total + 1;
		user.setName("Fan" + r);
		user.setEmail("fantongx@gmail.com");
		user.setPhone(Long.toString(r));
		users.create(user);
		users = (UserFeed) users.load();
		assertEquals(total + 1, users.getTotalResults());
	}

}
