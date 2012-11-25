package org.cloudlet.web.core.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;

@Category(ResourceCategory.class)
public interface CoreAutoBeanFactory extends AutoBeanFactory {

  AutoBean<Repository> createRepository();

  AutoBean<User> createUser();

  AutoBean<UserFeed> createUserFeed();
}