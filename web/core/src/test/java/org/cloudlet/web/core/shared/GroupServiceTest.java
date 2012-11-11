package org.cloudlet.web.core.shared;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.core.shared.DataGraph;
import org.cloudlet.web.core.shared.Group;
import org.cloudlet.web.core.shared.GroupFeed;
import org.cloudlet.web.core.shared.Repository;
import org.cloudlet.web.core.shared.User;
import org.cloudlet.web.core.shared.UserFeed;
import org.cloudlet.web.core.shared.View;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupServiceTest extends WebTest {

  @Inject
  CoreResourceConfig config;

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

    repo.loadRelationships();
    DataGraph dg = repo.load();

    JAXBContext jc =
        JAXBContext.newInstance(Repository.class, DataGraph.class, GroupFeed.class, UserFeed.class,
            User.class, View.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(dg, os);
    System.out.println(os.toString());
  }

  @Override
  protected ResourceConfig configure() {
    enable(TestProperties.LOG_TRAFFIC);
    return config;
  }

}
