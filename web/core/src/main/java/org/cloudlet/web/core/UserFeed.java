package org.cloudlet.web.core;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.cloudlet.web.core.service.UserFeedService;

@XmlRootElement
@Entity
@Handler(UserFeedService.class)
public class UserFeed extends Feed<User> {
	@Override
	public Class<User> getEntryType() {
		return User.class;
	}
}
