package org.cloudlet.web.core.server;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.service.GroupService;

import com.google.inject.Singleton;

@Singleton
public class GroupServiceImpl extends EntryServiceImpl<Group> implements
		GroupService {
}
