package org.cloudlet.web.core.bean;

import com.google.inject.Inject;

import org.cloudlet.web.core.service.GroupFeedBean;
import org.cloudlet.web.core.service.RepositoryBean;
import org.cloudlet.web.core.service.UserBean;
import org.cloudlet.web.core.service.UserFeedBean;
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

}
