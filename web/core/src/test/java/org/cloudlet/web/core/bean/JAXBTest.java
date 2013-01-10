package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import org.cloudlet.web.core.server.GroupFeed;
import org.cloudlet.web.core.server.Repository;
import org.cloudlet.web.core.server.User;
import org.cloudlet.web.core.server.UserFeed;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBTest extends CoreTest {

  @Inject
  Repository repo;

  @Inject
  UserFeed users;

  @Inject
  GroupFeed groups;

  @Test
  public void testJAXB() throws JAXBException {
    User user = new User();
    user.setName("user1");
    List<User> userList = new ArrayList<User>();
    userList.add(user);
    users.setEntries(userList);
    JAXBContext jc = JAXBContext.newInstance(UserFeed.class, User.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(users, os);
    System.out.println(os.toString());
  }

}
