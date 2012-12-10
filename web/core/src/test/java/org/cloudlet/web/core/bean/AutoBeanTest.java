/*
 * Copyright 2012 Retechcorp.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cloudlet.web.core.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.shared.Splittable;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

import org.cloudlet.web.core.CoreAutoBeanFactory;
import org.cloudlet.web.core.Repository;
import org.cloudlet.web.core.Resource;
import org.cloudlet.web.core.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AutoBeanTest {

  @Test
  public void testAutoBean() {
    CoreAutoBeanFactory factory = AutoBeanFactorySource.create(CoreAutoBeanFactory.class);
    AutoBean<Repository> repoBean = factory.createRepository();
    AutoBean<User> userBean = factory.createUser();
    List<Resource> children = new ArrayList<Resource>();
    User user = userBean.as();
    user.setId("u1");
    user.setTitle("User1");
    user.setResourceType("User");
    user.setDateCreated(new Date());
    user.setPhone("12345");
    user.setLeaf(true);
    user.setOwner(user);
    String userdata = AutoBeanCodex.encode(userBean).getPayload();
    System.out.println(userdata);

    children.add(user);
    Repository repo = repoBean.as();
    repo.setId("repo");
    repo.setTitle("Repository");
    repo.setChildren(children);
    Splittable s = AutoBeanCodex.encode(repoBean);
    String data = s.getPayload();
    System.out.println(data);

    AutoBean<Repository> repoBean2 = AutoBeanUtils.getAutoBean(repo);
    assertEquals(repoBean, repoBean2);

    AutoBean<Repository> decoded = AutoBeanCodex.decode(factory, Repository.class, s);
    Repository repo1 = decoded.as();
    List<Resource> c = repo1.getChildren();
    for (Resource r : c) {
      User u = (User) r;
      System.out.println(u.getPhone());
    }
    assertNotSame(repo, repo1);
  }

}
