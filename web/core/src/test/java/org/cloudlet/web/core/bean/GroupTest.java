package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

import org.cloudlet.web.core.server.Group;
import org.cloudlet.web.core.server.Groups;
import org.cloudlet.web.core.server.GroupService;
import org.cloudlet.web.core.server.Media;
import org.cloudlet.web.core.server.Repository;
import org.cloudlet.web.core.server.RepositoryService;
import org.cloudlet.web.core.server.User;
import org.cloudlet.web.core.server.Users;
import org.cloudlet.web.core.server.UserService;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupTest extends CoreTest {

  @Inject
  RepositoryService repoSvc;

  @Inject
  GroupService groupsSvc;

  @Inject
  UserService usersSvc;

  @Test
  public void testSubResource() throws JAXBException {
    Repository repo = repoSvc.getRoot();
    Groups groups = groupsSvc.getRoot();
    Users users = usersSvc.getRoot();
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
    long total = users.getTotalEntries();
    for (int i = 1; i <= 10; i++) {
      User user = users.newEntry();
      long count = total + i;
      user.setName("User " + count);
      user.setPath("user" + count);
      user.setEmail("user" + count + "@gmail.com");
      user.setPhone(Long.toString(count));
      users.createEntry(user);
      users.load();
      assertEquals(count, users.getTotalEntries());
    }
    repo.load();

    JAXBContext jc = JAXBContext.newInstance(Repository.class, Groups.class, Users.class, User.class, Media.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(repo, os);
    System.out.println(os.toString());
  }
}
