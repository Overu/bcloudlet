package org.cloudlet.web.core;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanFactory.Category;

@Category(ResourceCategory.class)
public interface CoreAutoBeanFactory extends AutoBeanFactory {

  AutoBean<Book> createBook();

  AutoBean<BookFeed> createBookFeed();

  AutoBean<Media> createMedia();

  AutoBean<Repository> createRepository();

  AutoBean<User> createUser();

  AutoBean<UserFeed> createUserFeed();
}