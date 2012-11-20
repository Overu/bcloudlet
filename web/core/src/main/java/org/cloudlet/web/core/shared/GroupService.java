package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.GroupServiceImpl;

@ImplementedBy(GroupServiceImpl.class)
public interface GroupService extends ResourceService<Group> {

}
