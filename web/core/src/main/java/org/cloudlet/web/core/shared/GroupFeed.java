package org.cloudlet.web.core.shared;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = GroupFeed.TYPE_NAME)
@Entity
@Handler(GroupFeedService.class)
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeed extends PagingFeed<Group> {

  public static final String TYPE_NAME = "GroupFeed";

  public static FeedType<GroupFeed, Group> TYPE = new FeedType<GroupFeed, Group>(PagingFeed.TYPE,
      TYPE_NAME, Group.TYPE) {
    @Override
    public GroupFeed createInstance() {
      return new GroupFeed();
    }
  };

  @Override
  public Class<Group> getEntryType() {
    return Group.class;
  }

  @Override
  public FeedType<GroupFeed, Group> getResourceType() {
    return TYPE;
  }
}
