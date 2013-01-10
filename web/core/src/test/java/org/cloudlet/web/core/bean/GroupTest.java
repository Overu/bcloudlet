package org.cloudlet.web.core.bean;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.Media;
import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.Root;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupTest extends CoreTest {

	@Inject
	Repository repo;

	@Root
	@Inject
	GroupFeed groups;

	@Root
	@Inject
	UserFeed users;

	@Test
	public void testSubResource() throws JAXBException {
		System.out.println(UUID.randomUUID().toString());
		Group group = groups.getEntry("mygroup");
		if (group == null) {
			group = groups.newEntry();
			group.setPath("mygroup");
			group = groups.createEntry(group);
		} else {
			group.load();
		}
		users.load();
		long total = users.getChildrenCount();
		for (int i = 1; i <= 10; i++) {
			User user = users.newEntry();
			long count = total + i;
			user.setName("User " + count);
			user.setPath("user" + count);
			user.setEmail("user" + count + "@gmail.com");
			user.setPhone(Long.toString(count));
			users.createEntry(user);
			users.load();
			assertEquals(count, users.getChildrenCount());
		}
		repo.loadChildren();
		repo.load();

		JAXBContext jc = JAXBContext.newInstance(Repository.class, GroupFeed.class, UserFeed.class, User.class,
				Media.class);
		Marshaller marshaller = jc.createMarshaller();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(repo, os);
		System.out.println(os.toString());
	}
}
