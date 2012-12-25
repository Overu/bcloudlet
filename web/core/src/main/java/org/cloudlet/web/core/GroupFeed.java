package org.cloudlet.web.core;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.service.GroupFeedBean;

@ImplementedBy(GroupFeedBean.class)
public interface GroupFeed extends PagingFeed<Group> {
  String TYPE = CorePackage.PREFIX + "GroupFeed";
}
