package com.goodow.web.core.jaxb;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.cloudlet.web.core.shared.Feed;
import org.cloudlet.web.core.shared.GroupService;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.test.WebTest;
import org.junit.Test;

import com.google.inject.Inject;

public class JAXBTest extends WebTest {

	@Inject
	GroupService groupService;

	@Test
	public void testJAXB() throws JAXBException {
		Feed<User> users = groupService.getUserService("g1").getFeed();
		JAXBContext jc = JAXBContext.newInstance(Feed.class, User.class);
		Marshaller marshaller = jc.createMarshaller();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(users, os);
		System.out.println(os.toString());
	}
}
