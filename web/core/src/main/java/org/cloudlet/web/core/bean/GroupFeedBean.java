package org.cloudlet.web.core.bean;

import org.cloudlet.web.core.service.GroupFeedService;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = GroupFeedBean.TYPE_NAME)
@XmlType(name = GroupFeedBean.TYPE_NAME)
@Entity
@Handler(GroupFeedService.class)
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeedBean extends PagingFeedBean<GroupBean> {

  public static final String TYPE_NAME = "GroupFeed";

  public static FeedType<GroupFeedBean, GroupBean> TYPE = new FeedType<GroupFeedBean, GroupBean>(
      PagingFeedBean.TYPE, TYPE_NAME, GroupBean.TYPE) {
    @Override
    public GroupFeedBean createInstance() {
      return new GroupFeedBean();
    }
  };

  @Override
  public Class<GroupBean> getEntryType() {
    return GroupBean.class;
  }

  @Override
  public FeedType<GroupFeedBean, GroupBean> getResourceType() {
    return TYPE;
  }
}
