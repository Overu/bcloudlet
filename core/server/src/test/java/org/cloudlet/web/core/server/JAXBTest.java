package org.cloudlet.web.core.server;

import com.google.inject.Inject;

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

  @Test
  public void testJAXB() throws JAXBException {
    Users users = repo.getUsers();
    User user = new User();
    user.setName("user1");
    List<User> userList = new ArrayList<User>();
    userList.add(user);
    users.setItems(userList);
    JAXBContext jc = JAXBContext.newInstance(Users.class, User.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(users, os);
    System.out.println(os.toString());
  }

}
