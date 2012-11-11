package org.cloudlet.web.core.shared;


import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Handler(GroupFeedService.class)
@Path("groups")
public class GroupFeed extends PagingFeed<Group> {

  public static FeedType<GroupFeed, Group> TYPE = new FeedType<GroupFeed, Group>(PagingFeed.TYPE,
      "groupFeed", Group.TYPE) {
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
