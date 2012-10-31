package org.cloudlet.web.core;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import org.cloudlet.web.core.service.GroupService;

@XmlRootElement
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "t_group")
@Handler(GroupService.class)
public class Group extends Entry {

	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Path("users")
	public UserFeed getUsers() {
		return (UserFeed) getChild("users");
	}

	@Path("groups")
	public GroupFeed getGroups() {
		return (GroupFeed) getChild("groups");
	}
}
