package org.cloudlet.web.core.server;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupTest extends CoreTest {

  @Inject
  Repository repo;

  @Test
  public void testSubResource() throws JAXBException {
    Groups groups = repo.getGroups();
    Users users = repo.getUsers();
    Group group = groups.getChild("mygroup");
    if (group == null) {
      group = groups.newEntry();
      group.setPath("mygroup");
      group = groups.doCreate(group);
    } else {
      group.load();
    }
    users.load();
    Long total = users.getCount();
    for (int i = 1; i <= 10; i++) {
      User user = users.newEntry();
      Long count = total + i;
      user.setName("User " + count);
      user.setPath("user" + count);
      user.setEmail("user" + count + "@gmail.com");
      user.setPhone(Long.toString(count));
      users.doCreate(user);
      users.load();
      assertEquals(count, users.getCount());
    }
    repo.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, Groups.class, Users.class, User.class, Media.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(repo, os);
    System.out.println(os.toString());
  }
}
