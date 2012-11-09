package org.cloudlet.web.core.service;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.CoreRepository;
import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.server.CoreResourceConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import java.util.UUID;

public class GroupServiceTest extends CoreRepositoryTest {

  @Inject
  CoreResourceConfig config;

  @Inject
  CoreRepository repo;

  @Test
  public void testSubResource() {
    System.out.println(UUID.randomUUID().toString());
    GroupFeed groups = repo.getGroups();
    Group group = groups.getEntry("mygroup");
    if (group == null) {
      group = new Group();
      group.setPath("mygroup");
      group = groups.createEntry(group);
    } else {
      group.load();
    }
    UserFeed users = repo.getUsers();
    users.load();
    long total = users.getTotalCount();
    User user = new User();
    long r = total + 1;
    user.setName("Fan" + r);
    user.setEmail("fantongx@gmail.com");
    user.setPhone(Long.toString(r));
    users.createEntry(user);
    users.load();
    assertEquals(total + 1, users.getTotalCount());
  }

  @Override
  protected ResourceConfig configure() {
    enable(TestProperties.LOG_TRAFFIC);
    return config;
  }

}
