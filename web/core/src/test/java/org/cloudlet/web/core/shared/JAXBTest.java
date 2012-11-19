package org.cloudlet.web.core.shared;

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

  @Test
  public void testMembers() throws JAXBException {
    Group group = groups.getEntry("mygroup");
    if (group == null) {
      group = new Group();
      group.setName("My Group");
      group.setPath("mygroup");
      groups.createEntry(group);
    }

    User user = new User();
    user.setName("user" + System.currentTimeMillis());
    user.setPath(user.getName());
    users.createEntry(user);

    MemberFeed members = group.getMembers();
    UserMember member1 = new UserMember();
    member1.setUser(user);
    member1.setRole(GroupRole.MANAGER);
    members.createEntry(member1);

    members.load();

    JAXBContext jc =
        JAXBContext.newInstance(MemberFeed.class, Member.class, User.class, UserFeed.class,
            Group.class, GroupFeed.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(members, os);
    System.out.println(os.toString());
  }

}
