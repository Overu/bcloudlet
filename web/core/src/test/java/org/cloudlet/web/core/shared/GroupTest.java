package org.cloudlet.web.core.shared;

import static org.junit.Assert.assertEquals;

import com.google.inject.Inject;

import org.cloudlet.web.core.bean.GroupBean;
import org.cloudlet.web.core.bean.GroupFeedBean;
import org.cloudlet.web.core.bean.MediaBean;
import org.cloudlet.web.core.bean.RepositoryBean;
import org.cloudlet.web.core.bean.UserBean;
import org.cloudlet.web.core.bean.UserFeedBean;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GroupTest extends CoreTest {

  @Inject
  RepositoryBean repo;

  @Inject
  GroupFeedBean groups;

  @Inject
  UserFeedBean users;

  @Test
  public void testSubResource() throws JAXBException {
    System.out.println(UUID.randomUUID().toString());
    GroupBean group = groups.getEntry("mygroup");
    if (group == null) {
      group = new GroupBean();
      group.setPath("mygroup");
      group = groups.createEntry(group);
    } else {
      group.load();
    }
    users.load();
    long total = users.getChildrenCount();
    UserBean user = new UserBean();
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
        JAXBContext.newInstance(RepositoryBean.class, GroupFeedBean.class, UserFeedBean.class,
            UserBean.class, MediaBean.class);
    Marshaller marshaller = jc.createMarshaller();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    marshaller.marshal(repo, os);
    System.out.println(os.toString());
  }

}
