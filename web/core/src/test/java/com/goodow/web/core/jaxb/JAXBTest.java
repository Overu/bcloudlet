package com.goodow.web.core.jaxb;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.WebPlatform;
import org.cloudlet.web.test.WebTest;
import org.junit.Test;

public class JAXBTest extends WebTest {

	@Test
	public void testJAXB() throws JAXBException {
		Group group = (Group) WebPlatform.getDefault().getRoot();
		Feed<User> users = group.getUsers();
		User user = new User();
		user.setName("user1");
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		users.setEntries(userList);
		JAXBContext jc = JAXBContext.newInstance(Feed.class, User.class);
		Marshaller marshaller = jc.createMarshaller();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(users, os);
		System.out.println(os.toString());
	}
}
