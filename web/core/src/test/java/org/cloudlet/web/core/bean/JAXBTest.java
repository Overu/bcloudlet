package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import org.cloudlet.web.core.GroupRole;
import org.cloudlet.web.core.service.GroupBean;
import org.cloudlet.web.core.service.GroupFeedBean;
import org.cloudlet.web.core.service.MemberBean;
import org.cloudlet.web.core.service.MemberFeedBean;
import org.cloudlet.web.core.service.RepositoryBean;
import org.cloudlet.web.core.service.UserBean;
import org.cloudlet.web.core.service.UserFeedBean;
import org.cloudlet.web.core.service.UserMember;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBTest extends CoreTest {

  @Inject
  RepositoryBean repo;

  @Inject
  UserFeedBean users;

  @Inject
  GroupFeedBean groups;

  @Test
  public void testJAXB() throws JAXBException {
    UserBean user = new UserBean();
    user.setName("user1");
    List<UserBean> userList = new ArrayList<UserBean>();
    userList.add(user);
    users.setEntries(userList);
    JAXBContext jc = JAXBContext.newInstance(UserFeedBean.class, UserBean.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(users, os);
    System.out.println(os.toString());
  }

  @Test
  public void testMembers() throws JAXBException {
    GroupBean group = groups.getEntry("mygroup");
    if (group == null) {
      group = new GroupBean();
      group.setName("My Group");
      group.setPath("mygroup");
      groups.createEntry(group);
    }

    UserBean user = new UserBean();
    user.setName("user" + System.currentTimeMillis());
    user.setPath(user.getName());
    users.createEntry(user);

    MemberFeedBean members = group.getMembers();
    UserMember member1 = new UserMember();
    member1.setUser(user);
    member1.setRole(GroupRole.MANAGER);
    members.createEntry(member1);

    members.load();

    JAXBContext jc =
        JAXBContext.newInstance(MemberFeedBean.class, MemberBean.class, UserBean.class,
            UserFeedBean.class, GroupBean.class, GroupFeedBean.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(members, os);
    System.out.println(os.toString());
  }

}
