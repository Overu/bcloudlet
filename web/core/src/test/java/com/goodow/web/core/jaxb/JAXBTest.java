package com.goodow.web.core.jaxb;

import com.google.inject.Inject;

import org.cloudlet.web.core.Feed;
import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.GroupFeed;
import org.cloudlet.web.core.GroupRole;
import org.cloudlet.web.core.Member;
import org.cloudlet.web.core.MemberFeed;
import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.User;
import org.cloudlet.web.core.UserFeed;
import org.cloudlet.web.core.UserMember;
import org.cloudlet.web.core.server.CoreResourceConfig;
import org.cloudlet.web.test.WebTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBTest extends WebTest {

  @Inject
  CoreResourceConfig config;

  @Inject
  Repository repo;

  @Test
  public void testJAXB() throws JAXBException {
    Feed<User> users = repo.getUsers();
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

  @Test
  public void testMembers() throws JAXBException {
    GroupFeed groups = repo.getGroups();
    Group group = groups.getChild("mygroup");
    if (group == null) {
      group = new Group();
      group.setName("My Group");
      group.setPath("mygroup");
      groups.create(group);
    }

    UserFeed users = repo.getUsers();
    User user = new User();
    user.setName("user" + System.currentTimeMillis());
    user.setPath(user.getName());
    users.create(user);

    MemberFeed members = group.getMembers();
    UserMember member1 = new UserMember();
    member1.setUser(user);
    member1.setRole(GroupRole.MANAGER);
    members.create(member1);

    members.load();

    JAXBContext jc =
        JAXBContext.newInstance(MemberFeed.class, Member.class, User.class, UserFeed.class,
            Group.class, GroupFeed.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(members, os);
    System.out.println(os.toString());
  }

  @Override
  protected ResourceConfig configure() {
    enable(TestProperties.LOG_TRAFFIC);
    return config;
  }

}
