package org.cloudlet.web.core.bean;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
@Entity
@Path("groups")
@DefaultField(key = "title", value = "用户组")
public class GroupFeedBean extends PagingFeedBean<GroupBean> {

	@Override
	public Class<GroupBean> getEntryType() {
		return GroupBean.class;
	}

}
