package org.cloudlet.web.core;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.cloudlet.web.core.service.GroupFeedService;

@XmlRootElement
@Entity
@Handler(GroupFeedService.class)
public class GroupFeed extends Feed<Group> {

}
