package org.cloudlet.web.core.shared;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupTest extends CoreTest {

  @Inject
  Repository repo;

  @Inject
  GroupFeed groups;

  @Inject
  UserFeed users;

  @Test
  public void testSubResource() throws JAXBException {
    System.out.println(UUID.randomUUID().toString());
    Group group = groups.getEntry("mygroup");
    if (group == null) {
      group = new Group();
      group.setPath("mygroup");
      group = groups.createEntry(group);
    } else {
      group.load();
    }
    users.load();
    long total = users.getChildrenCount();
    User user = new User();
    long r = total + 1;
    user.setName("Fan" + r);
    user.setEmail("fantongx@gmail.com");
    user.setPhone(Long.toString(r));
    users.createEntry(user);
    users.load();
    assertEquals(total + 1, users.getChildrenCount());

    repo.loadChildren();
    repo.load();

    JAXBContext jc =
        JAXBContext.newInstance(Repository.class, DataGraph.class, GroupFeed.class, UserFeed.class,
            User.class, Media.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(repo, os);
    System.out.println(os.toString());
  }

}
