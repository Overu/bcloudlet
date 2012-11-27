package org.cloudlet.web.core.service;

import org.cloudlet.web.core.GroupFeed;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = GroupFeed.TYPE)
@XmlType(name = GroupFeed.TYPE)
@Entity(name = GroupFeed.TYPE)
@Table(name = GroupFeed.TYPE)
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeedBean extends PagingFeedBean<GroupBean> {

  @Override
  public Class<GroupBean> getEntryType() {
    return GroupBean.class;
  }

  @Override
  public String getResourceType() {
    return GroupFeed.TYPE;
  }

}
