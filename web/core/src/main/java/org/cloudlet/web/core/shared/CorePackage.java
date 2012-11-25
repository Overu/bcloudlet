package org.cloudlet.web.core.shared;

import com.google.inject.Singleton;

import org.cloudlet.web.core.bean.BookBean;
import org.cloudlet.web.core.bean.BookFeedBean;
import org.cloudlet.web.core.bean.FeedBean;
import org.cloudlet.web.core.bean.GroupBean;
import org.cloudlet.web.core.bean.GroupFeedBean;
import org.cloudlet.web.core.bean.MediaBean;
import org.cloudlet.web.core.bean.MemberBean;
import org.cloudlet.web.core.bean.MemberFeedBean;
import org.cloudlet.web.core.bean.RepositoryBean;
import org.cloudlet.web.core.bean.ResourceBean;
import org.cloudlet.web.core.bean.SectionBean;
import org.cloudlet.web.core.bean.UserBean;
import org.cloudlet.web.core.bean.UserFeedBean;

@Singleton
public class CorePackage extends Package {

  public static final String NAME = "org.cloudlet.web.core.shared";

  public static final String PREFIX = "core";

  public CorePackage() {
    setName(NAME);
    addResourceType(ResourceBean.TYPE, FeedBean.TYPE, MediaBean.TYPE, RepositoryBean.TYPE);
    addResourceType(UserBean.TYPE, GroupBean.TYPE, MemberBean.TYPE, UserFeedBean.TYPE, GroupFeedBean.TYPE,
        MemberFeedBean.TYPE);
    addResourceType(BookBean.TYPE, BookFeedBean.TYPE, SectionBean.TYPE);
    init();
  }

  @Override
  protected void init() {
    super.init();
    // Operation op1 = new Operation();
    // op1.setHttpMethod("GET");
    // op1.setPath(Feed.ENTRIES);
    // op1.setType(Resource.TYPE);
    // Feed.TYPE.addOperation(op1);
    //
    // Operation op2 = new Operation();
    // op2.setHttpMethod("GET");
    // op2.setPath(Feed.NEW);
    // op2.setType(Resource.TYPE);
    // Feed.TYPE.addOperation(op2);
  }

}
