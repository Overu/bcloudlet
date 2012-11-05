package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.server.GroupServiceImpl;

@ImplementedBy(GroupServiceImpl.class)
public interface GroupService extends EntryService<Group> {

}
