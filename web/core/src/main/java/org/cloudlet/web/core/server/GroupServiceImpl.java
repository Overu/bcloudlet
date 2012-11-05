package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.Group;
import org.cloudlet.web.core.service.GroupService;

@Singleton
public class GroupServiceImpl extends EntryServiceImpl<Group> implements GroupService {
}
