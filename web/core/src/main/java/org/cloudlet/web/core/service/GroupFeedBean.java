package org.cloudlet.web.core.service;

import org.cloudlet.web.core.CorePackage;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = GroupFeedBean.TYPE)
@XmlType(name = GroupFeedBean.TYPE)
@Entity(name = GroupFeedBean.TYPE)
@Table(name = GroupFeedBean.TYPE)
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeedBean extends PagingFeedBean<GroupBean> {
  public static final String TYPE = CorePackage.PREFIX + "GroupFeed";

  @Override
  public Class<GroupBean> getEntryType() {
    return GroupBean.class;
  }

  @Override
  public String getResourceType() {
    return TYPE;
  }

}
