package org.cloudlet.web.core.bean;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.Root;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupTest extends CoreTest {

	@Inject
	RepositoryBean repo;

	@Root
	@Inject
	GroupFeedBean groups;

	@Root
	@Inject
	UserFeedBean users;

	@Test
	public void testSubResource() throws JAXBException {
		System.out.println(UUID.randomUUID().toString());
		GroupBean group = groups.getEntry("mygroup");
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
			UserBean user = users.newEntry();
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

		JAXBContext jc = JAXBContext.newInstance(RepositoryBean.class, GroupFeedBean.class, UserFeedBean.class, UserBean.class,
				MediaBean.class);
		Marshaller marshaller = jc.createMarshaller();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(repo, os);
		System.out.println(os.toString());
	}
}
