package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import org.cloudlet.web.core.server.Groups;
import org.cloudlet.web.core.server.GroupService;
import org.cloudlet.web.core.server.Repository;
import org.cloudlet.web.core.server.RepositoryService;
import org.cloudlet.web.core.server.User;
import org.cloudlet.web.core.server.Users;
import org.cloudlet.web.core.server.UserService;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBTest extends CoreTest {

  @Inject
  RepositoryService repoSvc;

  @Inject
  UserService usersSvc;

  @Inject
  GroupService groupsSvc;

  @Test
  public void testJAXB() throws JAXBException {
    Repository repo = repoSvc.getRoot();
    Users users = usersSvc.getRoot();
    Groups groups = groupsSvc.getRoot();
    User user = new User();
    user.setName("user1");
    List<User> userList = new ArrayList<User>();
    userList.add(user);
    users.setChildren(userList);
    JAXBContext jc = JAXBContext.newInstance(Users.class, User.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(users, os);
    System.out.println(os.toString());
  }

}
